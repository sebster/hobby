package com.sebster.weereld.hobbes.plugins.plato.subscription;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.JpaRepository;

@Component
public class PlatoSubscriptionJpaRepository extends JpaRepository<PlatoSubscription> {

	public PlatoSubscriptionJpaRepository() {
		super(PlatoSubscription.class);
	}

}
