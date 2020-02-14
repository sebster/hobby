package com.sebster.weereld.hobbes.plugins.plato;

import static com.sebster.weereld.hobbes.plugins.plato.PlatoSchedule.platoSchedule;
import static com.sebster.weereld.hobbes.plugins.plato.PlatoSubscription.platoSubscription;

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
public class PlatoScheduler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final PlatoPlugin platoPlugin;
	private final TaskScheduler taskScheduler;
	private final Repository<PlatoSubscription> repository;
	private final PlatoProperties properties;
	private final PlatformTransactionManager platformTransactionManager;

	private final Map<Long, ScheduledFuture<?>> scheduledSubscriptions = new HashMap<>();

	private TransactionTemplate getTransactionTemplate(TransactionDefinition transactionDefinition) {
		return new TransactionTemplate(platformTransactionManager, transactionDefinition);
	}

	private void executeInTransaction(Runnable action) {
		getTransactionTemplate(new DefaultTransactionDefinition()).execute(status -> {
			action.run();
			return null;
		});
	}

	@PostConstruct
	public void scheduleUnsolicitedQuotes() {
		executeInTransaction(() ->
				repository.findAll().forEach(this::addScheduleFor));
		if (properties.getUnsolicitedQuotesChatId() != 0L) {
			addScheduleFor(
					platoSubscription(properties.getUnsolicitedQuotesChatId(),
							platoSchedule(properties.getUnsolicitedQuotesIntervalLowerBound().toMillis(),
									properties.getUnsolicitedQuotesIntervalUpperBound().toMillis())));
		}
	}

	void addScheduleFor(PlatoSubscription subscription) {
		ScheduledFuture<?> future = taskScheduler.schedule(
				() -> platoPlugin.sendQuoteFromRandomEntry(subscription.getChatId()),
				new PlatoTrigger(subscription.getSchedule()));
		scheduledSubscriptions.put(subscription.getChatId(), future);
	}

	void updateScheduleFor(PlatoSubscription subscription) {
		cancelScheduleFor(subscription.getChatId());
		addScheduleFor(subscription);
	}

	void cancelScheduleFor(long chatId) {
		Optional.ofNullable(scheduledSubscriptions.get(chatId))
				.ifPresent(scheduledFuture -> scheduledFuture.cancel(true));
	}

	@AllArgsConstructor
	private static class PlatoTrigger implements Trigger {

		protected static final Logger logger = LoggerFactory.getLogger(PlatoTrigger.class);

		private final PlatoSchedule schedule;
		private final Random random = new Random();

		@Override
		public Date nextExecutionTime(TriggerContext triggerContext) {
			Date nextTime = determineNextTime(Objects.requireNonNullElseGet(triggerContext.lastActualExecutionTime(), Date::new));
			logger.debug("Next execution time: {}", nextTime);
			return nextTime;
		}

		private Date determineNextTime(Date lastTime) {
			long lowerBound = schedule.getIntervalLowerBound();
			long range = schedule.getIntervalUpperBound() - lowerBound;
			long delta = lowerBound + Math.abs(random.nextLong() % range);
			return new Date(lastTime.getTime() + delta);
		}

	}

}
