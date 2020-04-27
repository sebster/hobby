package com.sebster.commons.functions;

import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.function.Function;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Utility functions for manipulating {@link Function}s.
 */
@NoArgsConstructor(access = PRIVATE)
public class Functions {

	/**
	 * Make the specified function null safe by mapping {@code null} to {@code null} and delegating to the specified function for
	 * non-null arguments.
	 */
	public static <U, V> Function<U, V> nullSafe(@NonNull Function<U, V> function) {
		return u -> u != null ? function.apply(u) : null;
	}

	/**
	 * Return a function which unwraps an optional to the value if present and to {@code null} if it is empty.
	 */
	public static <U> Function<Optional<U>, U> unwrapOptional() {
		return optional -> optional.orElse(null);
	}

	/**
	 * Convert a function which returns an optional to a function which returns the value of the optional if present and {@code
	 * null} if it is empty.
	 */
	public static <T, V> Function<T, V> unwrapOptional(@NonNull Function<T, Optional<V>> optionalFunction) {
		return optionalFunction.andThen(unwrapOptional());
	}

}