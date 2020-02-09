package com.sebster.repository.api;

import lombok.Value;

@Value
public class PageRequest {

	int pageNumber;
	int pageSize;
	boolean includeTotalNumberOfItems;

	private PageRequest(int pageNumber, int pageSize, boolean includeTotalNumberOfItems) {
		if (pageSize < 1) {
			throw new IllegalArgumentException("Page size must be positive: " + pageSize);
		}
		if (pageNumber < 0) {
			throw new IllegalArgumentException("Page number must be non-negative: " + pageNumber);
		}
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.includeTotalNumberOfItems = includeTotalNumberOfItems;
	}

	public PageRequest pageSize(int pageSize) {
		return new PageRequest(pageNumber, pageSize, includeTotalNumberOfItems);
	}

	public PageRequest includeTotalNumberOfItems() {
		return new PageRequest(pageNumber, pageSize, true);
	}

	/**
	 * Construct a request for the specified page number in a collection given with the default page size. The request does not ask
	 * for the total number of elements in the collection; however, it may be provided anyway. The page request can be customized
	 * with the {@link #pageSize} and {@link #includeTotalNumberOfItems} methods.
	 */
	public static PageRequest page(int number) {
		return new PageRequest(number, 20, false);
	}

}
