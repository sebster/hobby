package com.sebster.weereld.hobbes.people;

import static com.sebster.weereld.hobbes.people.Partner.DATE;
import static com.sebster.weereld.hobbes.people.Partner_.date;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.properties.AbstractJpaPropertyAdapter;

@Component
public class PartnerJpaPropertyAdapter extends AbstractJpaPropertyAdapter<Partner> {

	public PartnerJpaPropertyAdapter() {
		map(DATE, date);
	}

}
