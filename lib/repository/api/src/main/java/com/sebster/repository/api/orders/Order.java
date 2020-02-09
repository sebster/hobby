package com.sebster.repository.api.orders;

import lombok.NonNull;

public interface Order<T> {

	int compare(T a, T b);

	default Order<T> reversed() {
		return new ReverseOrder<>(this);
	}

	default Order<T> thenBy(@NonNull Order<? super T> order) {
		if (order instanceof LexicographicOrder) {
			LexicographicOrder<T> lexicographicOrder = (LexicographicOrder<T>) order;
			return lexicographicOrder.thenBy(order);
		}
		return LexicographicOrder.of(this, order);
	}

	static <T> Order<T> nullsFirst(@NonNull Order<T> order) {
		if (order instanceof NullFirstOrder) {
			return order;
		}
		if (order instanceof NullLastOrder) {
			return new NullFirstOrder<>(((NullLastOrder<T>) order).getOrder());
		}
		return new NullFirstOrder<>(order);
	}

	static <T> Order<T> nullsLast(@NonNull Order<T> order) {
		if (order instanceof NullLastOrder) {
			return order;
		}
		if (order instanceof NullFirstOrder) {
			return new NullLastOrder<>(((NullFirstOrder<T>) order).getOrder());
		}
		return new NullLastOrder<>(order);
	}

}
