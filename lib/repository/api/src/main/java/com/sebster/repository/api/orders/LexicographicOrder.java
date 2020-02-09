package com.sebster.repository.api.orders;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	@Override
	public LexicographicOrder<T> thenBy(@NonNull Order<? super T> order) {
		List<Order<? super T>> orders = new ArrayList<>(this.orders);
		orders.add(order);
		return new LexicographicOrder<T>(unmodifiableList(orders));
	}

	public static <T> LexicographicOrder<T> of(@NonNull Order<? super T> order1, @NonNull Order<? super T> order2) {
		return new LexicographicOrder<>(List.of(order1, order2));
	}

	public static <T> LexicographicOrder<T> of(@NonNull Collection<? extends Order<? super T>> orders) {
		return new LexicographicOrder<>(List.copyOf(orders));
	}

}
