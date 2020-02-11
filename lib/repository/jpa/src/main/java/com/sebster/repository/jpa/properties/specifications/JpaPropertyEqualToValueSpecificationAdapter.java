package com.sebster.repository.jpa.properties.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.specifications.PropertyEqualToValue;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaPropertyEqualToValueSpecificationAdapter<T> extends AbstractJpaPropertySpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof PropertyEqualToValue;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		PropertyEqualToValue<T, ?> eq = cast(spec);
		Path<?> path = toJpaPath(eq.getProperty(), root);
		Object value = eq.getValue();
		return value != null ? cb.equal(path, value) : cb.isNull(path);
	}

}
