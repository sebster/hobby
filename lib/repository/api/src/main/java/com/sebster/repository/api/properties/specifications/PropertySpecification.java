package com.sebster.repository.api.properties.specifications;

import java.util.Collection;

import com.sebster.repository.api.properties.Property;
import com.sebster.repository.api.specifications.Specification;
import lombok.NonNull;

public interface PropertySpecification<T, V> extends Specification<T> {

	static <T, V> Specification<T> isNull(@NonNull Property<T, V> property) {
		return new PropertyIsNull<>(property);
	}

	static <T, V> Specification<T> isNotNull(@NonNull Property<T, V> property) {
		return Specification.not(isNull(property));
	}

	static <T, V> Specification<T> eq(@NonNull Property<T, V> property, V value) {
		return new PropertyEqualToValue<>(property, value);
	}

	static <T> Specification<T> eqIgnoreCase(@NonNull Property<T, String> property, String value) {
		return new PropertyEqualToStringIgnoreCase<>(property, value);
	}

	static <T> Specification<T> contains(@NonNull Property<T, String> property, @NonNull String value) {
		return new PropertyContainsString<>(property, value);
	}

	static <T> Specification<T> containsIgnoreCase(@NonNull Property<T, String> property, @NonNull String value) {
		return new PropertyContainsStringIgnoreCase<>(property, value);
	}

	static <T> Specification<T> isTrue(@NonNull Property<T, Boolean> property) {
		return new PropertyEqualToValue<>(property, true);
	}

	static <T> Specification<T> isFalse(@NonNull Property<T, Boolean> property) {
		return new PropertyEqualToValue<>(property, false);
	}

	static <T, V extends Number & Comparable<? super V>> Specification<T> lt(@NonNull Property<T, V> property, @NonNull V value) {
		return new PropertyLessThanValue<>(property, value);
	}

	static <T, V extends Number & Comparable<? super V>> Specification<T> gt(@NonNull Property<T, V> property, @NonNull V value) {
		return new PropertyGreaterThanValue<>(property, value);
	}

	static <T, V extends Number & Comparable<? super V>> Specification<T> le(@NonNull Property<T, V> property, @NonNull V value) {
		return new PropertyLessThanOrEqualToValue<>(property, value);
	}

	static <T, V extends Number & Comparable<? super V>> Specification<T> ge(@NonNull Property<T, V> property, @NonNull V value) {
		return new PropertyGreaterThanOrEqualToValue<>(property, value);
	}

	static <T> Specification<T> lengthBetween(@NonNull Property<T, String> property, int lowInclusive, int highInclusive) {
		return new PropertyStringLengthBetween<>(property, lowInclusive, highInclusive);
	}

	static <T, V> Specification<T> in(@NonNull Property<T, V> property, @NonNull Collection<? extends V> values) {
		return new PropertyInValues<>(property, values);
	}

}
