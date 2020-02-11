package com.sebster.repository.jpa.properties.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.specifications.PropertyEqualToStringIgnoreCase;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaPropertyEqualToStringIgnoreCaseSpecificationAdapter<T> extends AbstractJpaPropertySpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof PropertyEqualToStringIgnoreCase;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		PropertyEqualToStringIgnoreCase<T> eq = cast(spec);
		Path<String> path = toJpaPath(eq.getProperty(), root);
		String value = eq.getValue();
		return value != null ? cb.equal(cb.lower(path), value.toLowerCase()) : cb.isNull(path);
	}

}
