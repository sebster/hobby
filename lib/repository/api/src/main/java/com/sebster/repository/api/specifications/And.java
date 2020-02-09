package com.sebster.repository.api.specifications;

import static java.util.List.copyOf;
import static org.apache.commons.lang3.Validate.noNullElements;

import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
public final class And<T> implements Specification<T> {

	@Getter
	@NonNull List<Specification<T>> specifications;

	And(@NonNull Collection<? extends Specification<T>> specifications) {
		this.specifications = copyOf(noNullElements(specifications));
	}

	@Override
	public boolean isSatisfiedBy(T object) {
		return specifications.stream().allMatch(specification -> specification.isSatisfiedBy(object));
	}

}
