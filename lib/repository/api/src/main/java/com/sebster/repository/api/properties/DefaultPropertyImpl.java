package com.sebster.repository.api.properties;

import static lombok.AccessLevel.PACKAGE;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
@EqualsAndHashCode(of = { "domainClass", "name" })
class DefaultPropertyImpl<T, V> implements Property<T, V> {

	private final @NonNull Class<T> domainClass;
	private final @NonNull String name;
	private final @NonNull Function<T, V> getter;
	private final Function<T, Consumer<V>> setter;

	@Override
	public Function<T, V> getter() {
		return getter;
	}

	@Override
	public Function<T, Consumer<V>> setter() {
		if (isReadOnly()) {
			return t -> v -> {
				throw new UnsupportedOperationException("Property '" + getFullName() + "' is read-only and cannot be set.");
			};
		}
		return setter;
	}

	@Override
	public boolean isReadOnly() {
		return setter == null;
	}

	public String toString() {
		return getFullName();
	}

}
