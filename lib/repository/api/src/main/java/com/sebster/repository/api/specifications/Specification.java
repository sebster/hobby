package com.sebster.repository.api.specifications;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

@FunctionalInterface
public interface Specification<T> extends Predicate<T> {

	boolean isSatisfiedBy(T object);

	default boolean test(T object) {
		return isSatisfiedBy(object);
	}

	default void ifSatisfiedBy(T object, Consumer<T> action) {
		if (isSatisfiedBy(object)) {
			action.accept(object);
		}
	}

	default Specification<T> and(Specification<T> specification) {
		return and(this, specification);
	}

	default Specification<T> or(Specification<T> specification) {
		return or(this, specification);
	}

	default Specification<T> negate() {
		return not(this);
	}

	default <S extends T> Specification<S> restrict() {
		return this::isSatisfiedBy;
	}

	default <S extends T> Specification<S> restrictTo(Class<S> clazz) {
		return restrict();
	}

	static <S> Specification<S> any() {
		return new Any<>();
	}

	@SafeVarargs
	static <S> Specification<S> and(Specification<S>... specifications) {
		return and(asList(specifications));
	}

	static <S> Specification<S> and(Collection<? extends Specification<S>> specifications) {
		return new And<>(specifications);
	}

	@SafeVarargs
	static <S> Specification<S> or(Specification<S>... specifications) {
		return or(asList(specifications));
	}

	static <S> Specification<S> or(Collection<? extends Specification<S>> specifications) {
		return new Or<>(specifications);
	}

	static <S> Specification<S> not(Specification<S> specification) {
		return new Not<>(specification);
	}

}
