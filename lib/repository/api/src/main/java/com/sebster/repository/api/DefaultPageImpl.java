package com.sebster.repository.api;

import static java.util.List.copyOf;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = PRIVATE)
class DefaultPageImpl<T> implements Page<T> {

	Long totalNumberOfItems;
	int pageNumber;
	int pageSize;
	List<T> items;

	@Override
	public Optional<Long> getTotalNumberOfItems() {
		return Optional.ofNullable(totalNumberOfItems);
	}

	@Override
	public int getNumberOfItems() {
		return items.size();
	}

	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}

	@Override
	public <U> Page<U> map(Function<? super T, ? extends U> mapper) {
		return new DefaultPageImpl<>(totalNumberOfItems, pageNumber, pageSize, items.stream().map(mapper).collect(toList()));
	}

	static <U> Page<U> of(Long totalNumberOfItems, int pageNumber, int pageSize, List<U> items) {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("Page number must be non-negative: " + pageNumber);
		}
		if (pageSize < 1) {
			throw new IllegalArgumentException("Page size must be positive: " + pageSize);
		}
		if (totalNumberOfItems != null) {
			if (totalNumberOfItems < 0) {
				throw new IllegalArgumentException("Total number of items must be non-negative: " + totalNumberOfItems);
			}
			if ((pageNumber + 1) * pageSize <= totalNumberOfItems && items.size() < pageSize) {
				throw new IllegalArgumentException(
						"Too few elements in page: totalNumberOfItems=" + totalNumberOfItems + " pageNumber="
								+ pageNumber + " pageSize=" + pageSize + " numberOfItems=" + items.size());
			}
			if (pageNumber * pageSize + items.size() > totalNumberOfItems) {
				throw new IllegalArgumentException(
						"Too many elements in page: totalNumberOfItems=" + totalNumberOfItems + " pageNumber="
								+ pageNumber + " pageSize=" + pageSize + " numberOfItems=" + items.size());
			}
		}
		return new DefaultPageImpl<>(totalNumberOfItems, pageNumber, pageSize, copyOf(items));
	}

}
