package com.sebster.repository.jpa.properties.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.sebster.repository.api.properties.specifications.PropertyContainsString;
import com.sebster.repository.api.specifications.Specification;

@Component
public class JpaPropertyContainsStringSpecificationAdapter<T> extends AbstractJpaPropertySpecificationAdapter<T> {

	@Override
	public boolean canAdapt(Specification<?> spec) {
		return spec instanceof PropertyContainsString;
	}

	@Override
	public Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb) {
		PropertyContainsString<T> contains = cast(spec);
		Path<String> path = toJpaPath(contains.getProperty(), root);
		return predicates().contains(cb, path, contains.getSubstring());
	}

}
