package com.sebster.repository.api.orders;

import static lombok.AccessLevel.PACKAGE;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public class NullLastOrder<T> implements Order<T> {

	@NonNull Order<T> order;

	@Override
	public Order<T> reversed() {
		return new NullFirstOrder<>(order.reversed());
	}

	@Override
	public int compare(T a, T b) {
		if (a == null) {
			return b == null ? 0 : 1;
		} else if (b == null) {
			return -1;
		} else {
			return order.compare(a, b);
		}
	}

}
