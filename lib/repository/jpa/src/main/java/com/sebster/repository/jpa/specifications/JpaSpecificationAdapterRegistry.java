package com.sebster.repository.jpa.specifications;

import static java.util.List.copyOf;

import java.util.List;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.specifications.Specification;
import lombok.NonNull;

@Component
public class JpaSpecificationAdapterRegistry {

	private @NonNull List<JpaSpecificationAdapter<?>> adapters;

	public <T> Predicate toJpaPredicate(
			@NonNull Specification<T> specification,
			@NonNull Root<? extends T> root,
			@NonNull CommonAbstractCriteria criteria,
			@NonNull CriteriaBuilder criteriaBuilder
	) {
		for (JpaSpecificationAdapter<?> adapter : adapters) {
			if (adapter.canAdapt(specification)) {
				@SuppressWarnings("unchecked")
				JpaSpecificationAdapter<T> tAdapter = (JpaSpecificationAdapter<T>) adapter;
				return tAdapter.adapt(specification, root, criteria, criteriaBuilder);
			}
		}
		throw new UnsupportedOperationException("Unmapped specification: " + specification);
	}

	@Autowired
	public void setAdapters(@NonNull List<JpaSpecificationAdapter<?>> adapters) {
		this.adapters = copyOf(adapters);
	}

}
