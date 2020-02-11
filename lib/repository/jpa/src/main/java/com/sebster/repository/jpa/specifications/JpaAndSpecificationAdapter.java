package com.sebster.repository.jpa.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.specifications.And;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaAndSpecificationAdapter<T> extends AbstractJpaSpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof And;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		And<T> and = cast(spec);
		return cb.and(
				and.getSpecifications().stream()
						.map(subSpec -> toJpaPredicate(subSpec, root, crit, cb))
						.toArray(Predicate[]::new)
		);
	}

}
