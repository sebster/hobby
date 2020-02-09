package com.sebster.repository.api.specifications;

import static lombok.AccessLevel.PACKAGE;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public final class Not<T> implements Specification<T> {

	@NonNull Specification<T> specification;

	@Override
	public boolean isSatisfiedBy(T object) {
		return !specification.isSatisfiedBy(object);
	}

}
