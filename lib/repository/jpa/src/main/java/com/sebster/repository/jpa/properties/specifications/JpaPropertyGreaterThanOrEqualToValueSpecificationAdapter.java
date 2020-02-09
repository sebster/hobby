package com.sebster.repository.jpa.properties.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.specifications.PropertyGreaterThanOrEqualToValue;
import com.sebster.repository.api.properties.specifications.PropertyGreaterThanValue;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaPropertyGreaterThanOrEqualToValueSpecificationAdapter<T> extends AbstractJpaPropertySpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof PropertyGreaterThanOrEqualToValue;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		PropertyGreaterThanValue<T, ?> ge = cast(spec);
		return cb.ge(toJpaPath(ge.getProperty(), root), ge.getValue());
	}

}
