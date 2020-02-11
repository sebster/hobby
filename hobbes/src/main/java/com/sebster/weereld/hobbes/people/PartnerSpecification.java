package com.sebster.weereld.hobbes.people;

import static com.sebster.repository.api.properties.specifications.PropertySpecification.isNotNull;
import static com.sebster.weereld.hobbes.people.Partner.DATE;

import com.sebster.repository.api.specifications.Specification;

public interface PartnerSpecification extends Specification<Partner> {

	static Specification<Partner> hasDate() {
		return isNotNull(DATE);
	}

}
