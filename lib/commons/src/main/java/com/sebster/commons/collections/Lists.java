package com.sebster.commons.collections;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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

	public static <T> List<T> filter(@NonNull List<T> list, @NonNull Predicate<? super T> predicate) {
		return list.stream().filter(predicate).collect(toList());
	}

	public static <S, T> List<T> map(@NonNull List<S> list, @NonNull Function<? super S, ? extends T> mapper) {
		return list.stream().map(mapper).collect(toList());
	}

	public static <S, T> List<T> filterAndMap(
			@NonNull List<S> list,
			@NonNull Predicate<? super S> predicate,
			@NonNull Function<? super S, ? extends T> mapper
	) {
		return list.stream().filter(predicate).map(mapper).collect(toList());
	}

}
