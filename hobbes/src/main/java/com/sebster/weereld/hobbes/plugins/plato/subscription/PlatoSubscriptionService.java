package com.sebster.weereld.hobbes.plugins.plato.subscription;

import static com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoSchedule.platoSchedule;
import static com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoSubscription.platoSubscription;
import static com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoSubscriptionSpecification.withChatId;

import java.time.Duration;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.Repository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlatoSubscriptionService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final PlatoDefaultSchedulingIntervalProvider properties;
	private final Repository<PlatoSubscription> repository;
	private final PlatoSubscriptionScheduler scheduler;

	public void subscribe(long chatId) {
		subscribe(chatId, properties.getIntervalLowerBound(), properties.getIntervalUpperBound());
	}

	public void subscribe(long chatId, Duration interval) {
		subscribe(chatId, interval, Duration.ZERO);
	}

	public void subscribe(long chatId, Duration intervalLowerBound, Duration intervalUpperBound) {
		Optional<PlatoSubscription> subscription = repository.findOne(withChatId(chatId));
		PlatoSchedule schedule = platoSchedule(intervalLowerBound.toMillis(), intervalUpperBound.toMillis());

		if (subscription.isPresent()) {
			updateSubscription(subscription.get(), schedule);
		} else {
			newSubscription(chatId, schedule);
		}
	}

	private void updateSubscription(PlatoSubscription subscription, PlatoSchedule schedule) {
		subscription.setSchedule(schedule);
		scheduler.updateScheduledTaskFor(subscription);
		logger.debug("Updated subscription: {}", subscription);
	}

	private void newSubscription(long chatId, PlatoSchedule schedule) {
		PlatoSubscription newSubscription = platoSubscription(chatId, schedule);
		repository.add(newSubscription);
		scheduler.addScheduledTaskFor(newSubscription);
		logger.debug("Added subscription: {}", newSubscription);
	}

	public void unsubscribe(long chatId) {
		repository.removeAll(withChatId(chatId));
		scheduler.cancelScheduledTaskFor(chatId);
		logger.debug("Cancelled subscription for {}", chatId);
	}

}
