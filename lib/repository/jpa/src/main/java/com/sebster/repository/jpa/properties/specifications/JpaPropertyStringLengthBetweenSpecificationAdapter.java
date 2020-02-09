package com.sebster.repository.jpa.properties.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.specifications.PropertyStringLengthBetween;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaPropertyStringLengthBetweenSpecificationAdapter<T> extends AbstractJpaPropertySpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof PropertyStringLengthBetween;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		PropertyStringLengthBetween<T> between = cast(spec);
		Expression<Integer> length = cb.length(toJpaPath(between.getProperty(), root));
		return cb.and(cb.ge(length, between.getLowInclusive()), cb.lt(length, between.getHighInclusive()));
	}

}
