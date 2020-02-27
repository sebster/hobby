package com.sebster.repository.api.properties;

import static com.sebster.commons.functions.Consumers.curry;
import static com.sebster.commons.functions.Consumers.map;
import static com.sebster.commons.functions.Functions.nullSafe;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import lombok.NonNull;

public interface Property<T, V> {

	Class<T> getDomainClass();

	String getName();

	default String getFullName() {
		return getDomainClass().getName() + "." + getName();
	}

	Function<T, V> getter();

	Function<T, Consumer<V>> setter();

	boolean isReadOnly();

	default V getValue(@NonNull T object) {
		return getter().apply(object);
	}

	default void setValue(@NonNull T object, V value) {
		setter().apply(object).accept(value);
	}

	default <W> Property<T, W> transform(@NonNull Function<V, W> transform) {
		return property(getDomainClass(), getName(), getter().andThen(transform));
	}

	default <W> Property<T, W> transformNullSafe(@NonNull Function<V, W> transform) {
		return transform(nullSafe(transform));
	}

	default <W> Property<T, W> transform(@NonNull Function<V, W> transform, @NonNull Function<W, V> inverseTransform) {
		return property(getDomainClass(), getName(), getter().andThen(transform), setter().andThen(map(inverseTransform)));
	}

	default <W> Property<T, W> transformNullSafe(@NonNull Function<V, W> transform, @NonNull Function<W, V> inverseTransform) {
		return transform(nullSafe(transform), nullSafe(inverseTransform));
	}

	static <T, V> Property<T, V> property(@NonNull Class<T> domainClass, @NonNull String name, @NonNull Function<T, V> getter) {
		return new DefaultPropertyImpl<>(domainClass, name, getter, null);
	}

	static <T, V> Property<T, V> property(
			@NonNull Class<T> domainClass,
			@NonNull String name,
			@NonNull Function<T, V> getter,
			@NonNull Function<T, Consumer<V>> setter
	) {
		return new DefaultPropertyImpl<>(domainClass, name, getter, setter);
	}

	static <T, V> Property<T, V> property(
			@NonNull Class<T> domainClass,
			@NonNull String name,
			@NonNull Function<T, V> getter,
			@NonNull BiConsumer<T, V> setter
	) {
		return property(domainClass, name, getter, curry(setter));
	}

}
