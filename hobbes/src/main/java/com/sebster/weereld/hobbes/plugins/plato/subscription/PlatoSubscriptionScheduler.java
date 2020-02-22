package com.sebster.weereld.hobbes.plugins.plato.subscription;

import static java.util.Objects.requireNonNullElseGet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.sebster.repository.api.Repository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlatoSubscriptionScheduler {

	private final PlatoMessageService messageService;
	private final Repository<PlatoSubscription> repository;

	private final TaskScheduler taskScheduler;
	private final PlatformTransactionManager platformTransactionManager;

	private final Map<Long, ScheduledFuture<?>> scheduledSubscriptions = new HashMap<>();

	@PostConstruct
	public void scheduleSubscriptions() {
		executeInTransaction(() -> repository.findAll().forEach(this::addScheduledTaskFor));
	}

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

	private TransactionTemplate getTransactionTemplate(TransactionDefinition transactionDefinition) {
		return new TransactionTemplate(platformTransactionManager, transactionDefinition);
	}

	private void executeInTransaction(Runnable action) {
		getTransactionTemplate(new DefaultTransactionDefinition()).execute(status -> {
			action.run();
			return null;
		});
	}

	@AllArgsConstructor
	private static class PlatoTrigger implements Trigger {

		protected static final Logger logger = LoggerFactory.getLogger(PlatoTrigger.class);

		private final PlatoSchedule schedule;
		private final Random random = new Random();

		@Override
		public Date nextExecutionTime(TriggerContext triggerContext) {
			Date nextTime = determineNextTime(requireNonNullElseGet(triggerContext.lastActualExecutionTime(), Date::new));
			logger.debug("Next execution time for {}: {}", schedule, nextTime);
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
