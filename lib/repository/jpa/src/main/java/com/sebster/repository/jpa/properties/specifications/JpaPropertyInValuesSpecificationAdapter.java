package com.sebster.repository.jpa.properties.specifications;

import java.util.List;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.specifications.PropertyInValues;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaPropertyInValuesSpecificationAdapter<T> extends AbstractJpaPropertySpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof PropertyInValues;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		PropertyInValues<T, ?> in = cast(spec);
		List<?> values = in.getValues();
		return values.isEmpty() ? cb.or() : toJpaPath(in.getProperty(), root).in(values);
	}

}
