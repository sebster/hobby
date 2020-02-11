package com.sebster.repository.jpa.specifications;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.specifications.Specification;
import lombok.NonNull;

@Component
public abstract class AbstractJpaSpecificationAdapter<T> implements JpaSpecificationAdapter<T> {

	private @NonNull JpaSpecificationAdapterRegistry specificationAdapterRegistry;
	private @NonNull JpaPredicateFactory predicateFactory;

	protected final <S> Predicate toJpaPredicate(
			@NonNull Specification<? super S> spec,
			@NonNull Root<S> root,
			@NonNull CommonAbstractCriteria crit,
			@NonNull CriteriaBuilder cb
	) {
		return specificationAdapterRegistry.toJpaPredicate(spec, root, crit, cb);
	}

	protected final <U, S extends Specification<U>> S cast(@NonNull Specification<U> specification) {
		@SuppressWarnings("unchecked")
		S castSpecification = (S) specification;
		return castSpecification;
	}

	protected JpaPredicateFactory predicates() {
		return predicateFactory;
	}

	@Autowired
	void setSpecificationAdapterRegistry(@NonNull JpaSpecificationAdapterRegistry specificationAdapterRegistry) {
		this.specificationAdapterRegistry = specificationAdapterRegistry;
	}

	@Autowired
	public void setPredicateFactory(JpaPredicateFactory predicateFactory) {
		this.predicateFactory = predicateFactory;
	}

}
