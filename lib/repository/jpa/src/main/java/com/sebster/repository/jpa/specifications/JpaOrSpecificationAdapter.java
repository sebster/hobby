package com.sebster.repository.jpa.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.specifications.Or;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaOrSpecificationAdapter<T> extends AbstractJpaSpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof Or;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		Or<T> or = cast(spec);
		return cb.or(
				or.getSpecifications().stream()
						.map(subSpec -> toJpaPredicate(subSpec, root, crit, cb))
						.toArray(Predicate[]::new)
		);
	}

}
