package com.sebster.weereld.hobbes.plugins.plato.subscription;

import static java.util.Objects.requireNonNullElseGet;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
public class PlatoSubscriptionScheduler {

	private final PlatoMessageService messageService;
	private final TaskScheduler taskScheduler;

	private final Map<Long, ScheduledFuture<?>> scheduledSubscriptions = new HashMap<>();

	public void addScheduledTaskFor(PlatoSubscription subscription) {
		ScheduledFuture<?> future = taskScheduler.schedule(
				() -> messageService.sendQuoteFromRandomEntry(subscription.getChatId()),
				new PlatoTrigger(subscription.getSchedule()));
		scheduledSubscriptions.put(subscription.getChatId(), future);
	}

	public void updateScheduledTaskFor(PlatoSubscription subscription) {
		cancelScheduledTaskFor(subscription.getChatId());
		addScheduledTaskFor(subscription);
	}

	public void cancelScheduledTaskFor(long chatId) {
		Optional.ofNullable(scheduledSubscriptions.get(chatId))
				.ifPresent(scheduledFuture -> scheduledFuture.cancel(true));
	}

	public void cancelAllScheduledTasks() {
		scheduledSubscriptions.keySet().forEach(this::cancelScheduledTaskFor);
	}

	public void addScheduledTasksFor(Collection<PlatoSubscription> subscriptions) {
		subscriptions.forEach(this::addScheduledTaskFor);
	}

	@AllArgsConstructor
	@Slf4j
	private static class PlatoTrigger implements Trigger {

		private final PlatoSchedule schedule;
		private final Random random = new Random();

		@Override
		public Date nextExecutionTime(TriggerContext triggerContext) {
			Date nextTime = determineNextTime(requireNonNullElseGet(triggerContext.lastActualExecutionTime(), Date::new));
			log.debug("Next execution time for {}: {}", schedule, nextTime);
			return nextTime;
		}

		private Date determineNextTime(Date lastExecutionTime) {
			long lowerBound = schedule.getIntervalLowerBound();
			long upperBound = schedule.getIntervalUpperBound();

			if (upperBound == 0L) {
				return new Date(lastExecutionTime.getTime() + lowerBound);
			} else {
				long range = upperBound - lowerBound;
				long delta = lowerBound + Math.abs(random.nextLong() % range);
				return new Date(lastExecutionTime.getTime() + delta);
			}
		}

	}

}
