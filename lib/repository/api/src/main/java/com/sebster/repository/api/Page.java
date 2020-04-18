package com.sebster.repository.api;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Page<T> extends Iterable<T> {

	/**
	 * Get the page number of the current page. The page number is 0-based (the first page has number 0).
	 */
	int getPageNumber();

	/**
	 * Returns whether or not this is the first page.
	 */
	default boolean isFirst() {
		return getPageNumber() == 0;
	}

	/**
	 * Returns whether or not this is the last page, if available.
	 */
	default Optional<Boolean> isLast() {
		return getTotalNumberOfPages().map(pages -> getPageNumber() >= pages - 1);
	}

	/**
	 * Get the size of the current page. The page size is always non-negative. The number of elements on the page may be less than
	 * the page size (if it is the last page), or if the page is after the last page.
	 */
	int getPageSize();

	/**
	 * Get the number of elements on this page.
	 */
	int getNumberOfItems();

	/**
	 * Get the total number of items in the collection this page is a part of, if available.
	 */
	Optional<Long> getTotalNumberOfItems();

	/**
	 * Get the total number of pages of the collection this page is a part of, if available.
	 */
	default Optional<Long> getTotalNumberOfPages() {
		return getTotalNumberOfItems().map(total -> (total + getPageSize() - 1) / getPageSize());
	}

	/**
	 * Get whether or not the page has any content. The content could be empty if the collection this page is part of is empty or
	 * the page requested is past the end of the colleciton.
	 */
	default boolean hasItems() {
		return getNumberOfItems() > 0;
	}

	/**
	 * Get the contents of the page. The returned list is immutable.
	 */
	List<T> getItems();

	/**
	 * Map this page to a new page with the supplied mapper function.
	 */
	<U> Page<U> map(Function<? super T, ? extends U> mapper);

	/**
	 * Get a stream of the elements in this page.
	 */
	default Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	/**
	 * Construct a page with the specified page number, size, and items, without specifying a total number of items in the
	 * collection this page is part of.
	 */
	static <U> Page<U> of(int pageNumber, int pageSize, List<U> items) {
		return DefaultPageImpl.of(null, pageNumber, pageSize, items);
	}

	/**
	 * Construct a page with the specified page number, size, and items, and with the specified total number of items in the
	 * collection this page is part of.
	 */
	static <U> Page<U> of(long totalNumberOfItems, int pageNumber, int pageSize, List<U> items) {
		return DefaultPageImpl.of(totalNumberOfItems, pageNumber, pageSize, items);
	}

}
