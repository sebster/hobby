package com.sebster.weereld.hobbes.plugins.plato;

import static com.sebster.weereld.hobbes.plugins.plato.PlatoSubscription.CHAT_ID;
import static com.sebster.weereld.hobbes.plugins.plato.PlatoSubscription_.chatId;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.properties.AbstractJpaPropertyAdapter;

@Component
public class PlatoSubscriptionJpaPropertyAdapter extends AbstractJpaPropertyAdapter<PlatoSubscription> {

	public PlatoSubscriptionJpaPropertyAdapter() {
		map(CHAT_ID, chatId);
	}

}

