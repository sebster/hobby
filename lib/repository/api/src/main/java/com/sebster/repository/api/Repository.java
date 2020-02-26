package com.sebster.repository.api;

import static com.sebster.repository.api.PageRequest.page;
import static com.sebster.repository.api.specifications.Specification.any;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.sebster.repository.api.orders.Order;
import com.sebster.repository.api.specifications.Specification;
import lombok.NonNull;

/**
 * Generic repository for domain objects.
 * <p>
 * NOTE: The runtime exceptions thrown by the methods in this interface are indicative, not definitive. The listed exceptions
 * indicate a programming error. As they are runtime exceptions, they should never be caught.
 *
 * @param <T> the type of the domain object
 */
public interface Repository<T> {

	/**
	 * Find the domain object satisfying the given specification.
	 *
	 * @throws IllegalStateException         if multiple domain objects satisfy the specification
	 * @throws UnsupportedOperationException if the given specification is unsupported
	 */
	default Optional<T> findOne(@NonNull Specification<? super T> specification) {
		List<T> top = findAll(specification, page(0).pageSize(2)).getItems();
		if (top.size() > 1) {
			throw new IllegalStateException("More than one object matches specification: " + specification);
		}
		return top.stream().findFirst();
	}

	/**
	 * Get the domain object satisfying the given specification.
	 *
	 * @throws IllegalStateException         if zero or multiple domain objects satisfy the specification
	 * @throws UnsupportedOperationException if the given specification is unsupported
	 */
	default T getOne(@NonNull Specification<? super T> specification) {
		return findOne(specification)
				.orElseThrow(() -> new IllegalStateException("No object matched by specification " + specification));
	}

	/**
	 * Return whether or not any domain object matches the given specification.
	 *
	 * @throws UnsupportedOperationException if the given specification is unsupported
	 */
	default boolean anyMatch(@NonNull Specification<? super T> specification) {
		return findAll(specification, page(0).pageSize(1)).stream().findAny().isPresent();
	}

	/**
	 * Return true if no domain object exists which matches the given specification.
	 *
	 * @throws UnsupportedOperationException if the given specification is unsupported
	 */
	default boolean noneMatch(@NonNull Specification<? super T> specification) {
		return !anyMatch(specification);
	}

	/**
	 * Find the first domain object satisfying the given specification with the given order.
	 *
	 * @throws UnsupportedOperationException if the given specification or order is unsupported
	 */
	default Optional<T> findFirst(@NonNull Specification<? super T> specification, @NonNull Order<? super T> order) {
		return findAll(specification, order, page(0).pageSize(1)).stream().findAny();
	}

	/**
	 * Get the last domain object satisfying the given specification with the given order.
	 *
	 * @throws IllegalStateException         if no domain objects satisfy the specification
	 * @throws UnsupportedOperationException if the given specification or order is unsupported
	 */
	default T getFirst(@NonNull Specification<? super T> specification, @NonNull Order<? super T> order) {
		return findFirst(specification, order)
				.orElseThrow(() -> new IllegalStateException("No object matched by specification " + specification));
	}

	/**
	 * Find the last domain object satisfying the given specification with the given order.
	 *
	 * @throws UnsupportedOperationException if the given specification or order is unsupported
	 */
	default Optional<T> findLast(@NonNull Specification<? super T> specification, @NonNull Order<? super T> order) {
		return findAll(specification, order.reversed(), page(0).pageSize(1)).stream().findAny();
	}

	/**
	 * Get the last domain object satisfying the given specification with the given order.
	 *
	 * @throws IllegalStateException         if no domain objects satisfy the specification
	 * @throws UnsupportedOperationException if the given specification or order is unsupported
	 */
	default T getLast(@NonNull Specification<? super T> specification, @NonNull Order<? super T> order) {
		return findLast(specification, order)
				.orElseThrow(() -> new IllegalStateException("No object matched by specification " + specification));
	}

	/**
	 * Find all domain objects.
	 */
	default Stream<T> findAll() {
		return findAll(any());
	}

	/**
	 * Find all domain objects satisfying the given specification.
	 *
	 * @throws UnsupportedOperationException if the given specification is unsupported
	 */
	Stream<T> findAll(@NonNull Specification<? super T> specification);

	/**
	 * Find all domain objects satisfying the given specification, ordered with the given order.
	 *
	 * @throws UnsupportedOperationException if the given specification or order is unsupported
	 */
	Stream<T> findAll(@NonNull Specification<? super T> specification, @NonNull Order<? super T> order);

	/**
	 * Find a page of domain objects satisfying the given specification and page request. If the request page number is larger than
	 * the total number of pages, an empty page is returned.
	 *
	 * @throws UnsupportedOperationException if the given specification is unsupported
	 */
	Page<T> findAll(@NonNull Specification<? super T> specification, @NonNull PageRequest pageRequest);

	/**
	 * Find a page of domain objects satisfying the given specification, page request, and order. If the request page number is
	 * larger than the total number of pages, an empty page is returned. The order applies to the entire collection, so the returned
	 * page is a page of the ordered collection.
	 *
	 * @throws UnsupportedOperationException if the given specification or order is unsupported
	 */
	Page<T> findAll(
			@NonNull Specification<? super T> specification,
			@NonNull Order<? super T> order,
			@NonNull PageRequest pageRequest
	);

	/**
	 * Count the number of domain objects satisfying the given specification.
	 *
	 * @throws UnsupportedOperationException if the given specification is unsupported
	 */
	long count(@NonNull Specification<? super T> specification);

	/**
	 * Add the specified domain object to the repository.
	 *
	 * @throws IllegalStateException if the domain object is already contained in this repository
	 */
	void add(@NonNull T object);

	/**
	 * Add the specified domain objects to the repository.
	 *
	 * @throws IllegalStateException if one of the domain objects is already contained in this repository
	 */
	default void addAll(@NonNull Collection<? extends T> objects) {
		objects.forEach(this::add);
	}

	/**
	 * Remove the specified domain object from this repository.
	 *
	 * @throws IllegalStateException if the domain object is not contained in this repository
	 */
	void remove(@NonNull T object);

	/**
	 * Remove all domain objects satisfying the given specification.
	 *
	 * @throws UnsupportedOperationException if the given specification is unsupported
	 */
	default void removeAll(@NonNull Specification<? super T> specification) {
		findAll(specification).forEach(this::remove);
	}

}
