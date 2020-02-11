package com.sebster.repository.api.orders;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PRIVATE)
public class LexicographicOrder<T> implements Order<T> {

	@NonNull List<Order<? super T>> orders;

	@Override
	public int compare(T a, T b) {
		for (Order<? super T> order : orders) {
			int c = order.compare(a, b);
			if (c != 0) {
				return c;
			}
		}
		return 0;
	}

	@Override
	public Order<T> reversed() {
		return new LexicographicOrder<>(orders.stream().map(Order::reversed).collect(toList()));
	}

	static <T> LexicographicOrder<T> of(@NonNull Collection<? extends Order<? super T>> orders) {
		return new LexicographicOrder<T>(orders.stream().flatMap(LexicographicOrder::unwrap).collect(toList()));
	}

	private static <T> Stream<Order<? super T>> unwrap(Order<T> order) {
		if (order instanceof LexicographicOrder) {
			LexicographicOrder<T> lexicographicOrder = (LexicographicOrder<T>) order;
			return lexicographicOrder.getOrders().stream();
		} else {
			return Stream.of(order);
		}
	}

}
