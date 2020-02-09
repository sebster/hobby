package com.sebster.repository.jpa.properties.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.specifications.PropertyLessThanOrEqualToValue;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaPropertyLessThanOrEqualToValueSpecificationAdapter<T> extends AbstractJpaPropertySpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof PropertyLessThanOrEqualToValue;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		PropertyLessThanOrEqualToValue<T, ?> le = cast(spec);
		return cb.le(toJpaPath(le.getProperty(), root), le.getValue());
	}

}
