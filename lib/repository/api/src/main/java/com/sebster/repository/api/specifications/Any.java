package com.sebster.repository.api.specifications;

import static lombok.AccessLevel.PACKAGE;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public final class Any<T> implements Specification<T> {

	@Override
	public boolean isSatisfiedBy(T object) {
		return true;
	}

}
