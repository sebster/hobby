package com.sebster.repository.api.properties.orders;

import static java.util.Comparator.naturalOrder;
import static lombok.AccessLevel.PACKAGE;

import java.util.Comparator;

import com.sebster.repository.api.properties.Property;
import com.sebster.repository.api.orders.Order;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = PACKAGE)
public class PropertyOrder<T, V extends Comparable<? super V>> implements Order<T> {

	@NonNull Property<T, V> property;

	@Override
	public int compare(@NonNull T a, @NonNull T b) {
		V va = property.getValue(a);
		V vb = property.getValue(b);
		Comparator<V> valueComparator = naturalOrder();
		return valueComparator.compare(va, vb);
	}

	public static <T, V extends Comparable<? super V>> PropertyOrder<T, V> orderBy(@NonNull Property<T, V> property) {
		return new PropertyOrder<>(property);
	}

}