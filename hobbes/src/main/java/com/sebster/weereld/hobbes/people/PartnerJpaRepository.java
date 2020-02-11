package com.sebster.weereld.hobbes.people;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.JpaRepository;

@Component
public class PartnerJpaRepository extends JpaRepository<Partner> {

	public PartnerJpaRepository() {
		super(Partner.class);
	}

}
