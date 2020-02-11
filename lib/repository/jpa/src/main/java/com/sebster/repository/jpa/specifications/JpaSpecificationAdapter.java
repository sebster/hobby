package com.sebster.repository.jpa.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.sebster.repository.api.specifications.Specification;

public interface JpaSpecificationAdapter<T> {

	boolean canAdapt(Specification<?> spec);

	Predicate adapt(Specification<T> spec, Root<? extends T> root, CommonAbstractCriteria crit, CriteriaBuilder cb);

}
