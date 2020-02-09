package com.sebster.repository.mem;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.sebster.repository.api.Page;
import com.sebster.repository.api.PageRequest;
import com.sebster.repository.api.Repository;
import com.sebster.repository.api.orders.Order;
import com.sebster.repository.api.specifications.Specification;
import lombok.NonNull;

public class InMemoryRepository<T> implements Repository<T> {

	private final List<T> data = new ArrayList<>();

	@Override
	public Stream<T> findAll(@NonNull Specification<? super T> specification) {
		return data.stream().filter(specification::isSatisfiedBy);
	}

	@Override
	public Stream<T> findAll(@NonNull Specification<? super T> specification, @NonNull Order<? super T> order) {
		return findAll(specification).sorted(order::compare);
	}

	@Override
	public Page<T> findAll(@NonNull Specification<? super T> specification, @NonNull PageRequest pageRequest) {
		return selectPage(findAll(specification), () -> count(specification), pageRequest);
	}

	@Override
	public Page<T> findAll(
			@NonNull Specification<? super T> specification,
			@NonNull Order<? super T> order,
			@NonNull PageRequest pageRequest
	) {
		return selectPage(findAll(specification, order), () -> count(specification), pageRequest);
	}

	@Override
	public long count(@NonNull Specification<? super T> specification) {
		return findAll(specification).count();
	}

	@Override
	public void add(@NonNull T object) {
		if (data.contains(object)) {
			throw new IllegalStateException("Object already present in repository: " + object);
		}
		data.add(object);
	}

	@Override
	public void remove(@NonNull T object) {
		boolean success = data.remove(object);
		if (!success) {
			throw new IllegalStateException("Object not present in repository: " + object);
		}
	}

	@Override
	public void removeAll(@NonNull Specification<? super T> specification) {
		data.removeIf(specification::isSatisfiedBy);
	}

	private Page<T> selectPage(Stream<T> allItems, Supplier<Long> countQuery, PageRequest pageRequest) {
		List<T> pageItems = allItems
				.skip(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.limit(pageRequest.getPageSize())
				.collect(toList());
		return pageRequest.isIncludeTotalNumberOfItems() ?
				Page.of(countQuery.get(), pageRequest.getPageNumber(), pageRequest.getPageSize(), pageItems) :
				Page.of(pageRequest.getPageSize(), pageRequest.getPageSize(), pageItems);
	}

}
