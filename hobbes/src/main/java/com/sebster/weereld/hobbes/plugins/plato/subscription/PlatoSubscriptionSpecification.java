package com.sebster.weereld.hobbes.plugins.plato.subscription;

import static com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoSubscription.CHAT_ID;

import com.sebster.repository.api.properties.specifications.PropertySpecification;
import com.sebster.repository.api.specifications.Specification;

public interface PlatoSubscriptionSpecification extends Specification<PlatoSubscription> {

	static Specification<PlatoSubscription> withChatId(long chatId) {
		return PropertySpecification.eq(CHAT_ID, chatId);
	}

}
