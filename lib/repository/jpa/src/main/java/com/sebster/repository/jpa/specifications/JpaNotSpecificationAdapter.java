package com.sebster.repository.jpa.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.specifications.Not;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaNotSpecificationAdapter<T> extends AbstractJpaSpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof Not;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		Not<T> not = cast(spec);
		return cb.not(toJpaPredicate(not.getSpecification(), root, crit, cb));
	}

}
