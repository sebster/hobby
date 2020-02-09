package com.sebster.repository.api.properties;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import lombok.NonNull;

public interface Property<T, V> extends Function<T, V>, BiConsumer<T, V> {

	Class<T> getDomainClass();

	String getName();

	default String getFullName() {
		return getDomainClass().getName() + "." + getName();
	}

	V getValue(@NonNull T object);

	void setValue(@NonNull T object, V value);

	default V apply(@NonNull T object) {
		return getValue(object);
	}

	default void accept(@NonNull T object, V value) {
		setValue(object, value);
	}

	static <T, V> Property<T, V> property(@NonNull Class<T> domainClass, @NonNull String name, @NonNull Function<T, V> getter) {
		return new DefaultPropertyImpl<>(domainClass, name, getter, null);
	}

	static <T, V> Property<T, V> optionalProperty(
			@NonNull Class<T> domainClass,
			@NonNull String name,
			@NonNull Function<T, Optional<V>> getter
	) {
		return new DefaultPropertyImpl<>(domainClass, name, object -> getter.apply(object).orElse(null), null);
	}

	static <T, V> Property<T, V> property(
			@NonNull Class<T> domainClass,
			@NonNull String name,
			@NonNull Function<T, V> getter,
			@NonNull BiConsumer<T, V> setter
	) {
		return new DefaultPropertyImpl<>(domainClass, name, getter, setter);
	}

	static <T, V> Property<T, V> optionalProperty(
			@NonNull Class<T> domainClass,
			@NonNull String name,
			@NonNull Function<T, Optional<V>> getter,
			@NonNull BiConsumer<T, V> setter
	) {
		return new DefaultPropertyImpl<>(domainClass, name, object -> getter.apply(object).orElse(null), setter);
	}

}
