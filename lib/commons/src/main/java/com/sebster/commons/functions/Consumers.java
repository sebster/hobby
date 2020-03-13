package com.sebster.commons.functions;

import static lombok.AccessLevel.PRIVATE;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * Utility functions for manipulating {@link Consumer}s.
 */
@AllArgsConstructor(access = PRIVATE)
public class Consumers {

	/**
	 * Convert a bi-consumer to function returning a consumer by currying the specified bi-consumer.
	 */
	public static <U, V> Function<U, Consumer<V>> curry(@NonNull BiConsumer<U, V> biConsumer) {
		return u -> v -> biConsumer.accept(u, v);
	}

	/**
	 * Convert a function returning a consumer to a bi-consumer by uncurrying.
	 */
	public static <U, V> BiConsumer<U, V> uncurry(@NonNull Function<U, Consumer<V>> function) {
		return (u, v) -> function.apply(u).accept(v);
	}

	/**
	 * Return a function which converts a consumer to a new consumer which transforms the argument to be consumed with the specified
	 * transform function first.
	 */
	public static <U, V> Function<Consumer<U>, Consumer<V>> map(@NonNull Function<? super V, ? extends U> transform) {
		return uConsumer -> v -> uConsumer.accept(transform.apply(v));
	}

}
