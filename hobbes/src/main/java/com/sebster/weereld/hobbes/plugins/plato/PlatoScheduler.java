package com.sebster.weereld.hobbes.plugins.plato;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlatoScheduler {

	private final PlatoPlugin platoPlugin;
	private final TaskScheduler taskScheduler;
	private final PlatoProperties properties;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@PostConstruct
	public void scheduleUnsolicitedQuotes() {
		if (properties.unsolicitedQuotesChatId != 0L) {
			taskScheduler.schedule(
					() -> platoPlugin.sendQuoteFromRandomEntry(properties.unsolicitedQuotesChatId),
					new PlatoTrigger(properties));
		}
	}

	@AllArgsConstructor
	private static class PlatoTrigger implements Trigger {

		protected static final Logger logger = LoggerFactory.getLogger(PlatoTrigger.class);

		private final PlatoProperties properties;
		private final Random random = new Random();

		@Override
		public Date nextExecutionTime(TriggerContext triggerContext) {
			Date nextTime = determineNextTime(Objects.requireNonNullElseGet(triggerContext.lastActualExecutionTime(), Date::new));
			logger.info("Next execution time: {}", nextTime);
			return nextTime;
		}

		private Date determineNextTime(Date lastTime) {
			long lowerBound = properties.getUnsolicitedQuotesIntervalLowerBound().toMillis();
			long range = properties.getUnsolicitedQuotesIntervalUpperBound().toMillis() - lowerBound;
			long delta = lowerBound + Math.abs(random.nextLong() % range);
			return new Date(lastTime.getTime() + delta);
		}

	}

}
