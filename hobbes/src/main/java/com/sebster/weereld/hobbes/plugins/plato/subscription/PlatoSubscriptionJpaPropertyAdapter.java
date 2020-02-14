package com.sebster.weereld.hobbes.plugins.plato.subscription;

import static com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoSubscription_.chatId;
import static com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoSubscription.CHAT_ID;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.properties.AbstractJpaPropertyAdapter;


@Component
public class PlatoSubscriptionJpaPropertyAdapter extends AbstractJpaPropertyAdapter<PlatoSubscription> {

	public PlatoSubscriptionJpaPropertyAdapter() {
		map(CHAT_ID, chatId);
	}

}

