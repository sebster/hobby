package com.sebster.repository.api.orders;

import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.NonNull;

public interface Order<T> {

	int compare(T a, T b);

	default Order<T> reversed() {
		return new ReverseOrder<>(this);
	}

	default <S extends T> Order<S> restrict() {
		@SuppressWarnings("unchecked")
		Order<S> restricted = (Order<S>) this;
		return restricted;
	}

	default Order<T> thenBy(@NonNull Order<? super T> order) {
		return this.thenBy(singletonList(order));
	}

	default Order<T> thenBy(@NonNull Collection<? extends Order<? super T>> orders) {
		List<Order<? super T>> newOrders = new ArrayList<>();
		newOrders.add(this);
		newOrders.addAll(orders);
		return orderBy(newOrders);
	}

	static <T> Order<T> orderBy(@NonNull Collection<? extends Order<? super T>> orders) {
		return LexicographicOrder.of(orders);
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
