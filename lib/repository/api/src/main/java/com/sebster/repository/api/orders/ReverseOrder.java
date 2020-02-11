package com.sebster.repository.api.orders;

import static lombok.AccessLevel.PACKAGE;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public class ReverseOrder<T> implements Order<T> {

	@NonNull Order<T> order;

	@Override
	public Order<T> reversed() {
		return order;
	}

	@Override
	public int compare(T a, T b) {
		return order.compare(b, a);
	}

}
