package com.sebster.weereld.hobbes.plugins.earlybird;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.JpaRepository;

@Component
public class EarlyBirdJpaRepository extends JpaRepository<EarlyBird> {

	public EarlyBirdJpaRepository() {
		super(EarlyBird.class);
	}

}
