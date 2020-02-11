package com.sebster.repository.api.properties;

import static lombok.AccessLevel.PACKAGE;

import java.util.function.BiConsumer;
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
	private final BiConsumer<T, V> setter;

	@Override
	public V getValue(T object) {
		return getter.apply(object);
	}

	@Override
	public void setValue(@NonNull T object, V value) {
		if (setter == null) {
			throw new UnsupportedOperationException("Property '" + getFullName() + "' is read-only and cannot be set.");
		}
		setter.accept(object, value);
	}

	public String toString() {
		return getFullName();
	}

}
