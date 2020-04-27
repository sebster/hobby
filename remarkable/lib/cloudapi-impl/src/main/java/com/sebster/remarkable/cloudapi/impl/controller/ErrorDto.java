package com.sebster.remarkable.cloudapi.impl.controller;

import java.util.Optional;
import java.util.function.Function;

public interface ErrorDto<T extends ErrorDto<T>> {

	Optional<String> getErrorMessage();

	default boolean hasError() {
		return getErrorMessage().isPresent();
	}

	default <E extends RuntimeException> T throwOnError() {
		return throwOnError(RuntimeException::new);
	}

	default <E extends RuntimeException> T throwOnError(Function<String, E> exceptionFactory) {
		if (hasError()) {
			throw exceptionFactory.apply(getErrorMessage().orElse("Unknown error"));
		}
		@SuppressWarnings("unchecked")
		T self = (T) this;
		return self;
	}

}
