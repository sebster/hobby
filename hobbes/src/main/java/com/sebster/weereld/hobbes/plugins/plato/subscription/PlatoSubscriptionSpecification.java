package com.sebster.weereld.hobbes.plugins.plato.subscription;

import static com.sebster.repository.api.properties.specifications.PropertySpecification.eq;
import static com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoSubscription.CHAT_ID;

import com.sebster.repository.api.specifications.Specification;

public interface PlatoSubscriptionSpecification extends Specification<PlatoSubscription> {

	static Specification<PlatoSubscription> withChatId(long chatId) {
		return eq(CHAT_ID, chatId);
	}

}
