package com.sebster.commons.collections;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = PRIVATE)
public class Lists {

	/**
	 * Reverse the specified list. The returned list is a new list, the original list is not modified. This method exists because
	 * {@link java.util.Collections#reverse(List)} behavior of modifying its argument is awkward..
	 */
	public static <T> List<T> reverse(@NonNull List<T> list) {
		List<T> result = new ArrayList<>(list);
		Collections.reverse(result);
		return result;
	}

	public static <U, V> List<V> map(@NonNull List<U> list, Function<U, V> function) {
		return list.stream().map(function).collect(toList());
	}

}
