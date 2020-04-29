package com.sebster.commons.streams;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static lombok.AccessLevel.PRIVATE;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = PRIVATE)
public class Streams {

	public static <T> Stream<T> stream(@NonNull Iterable<T> iterable) {
		return stream(iterable.iterator());
	}

	private static <T> Stream<T> stream(@NonNull Iterator<T> iterator) {
		return StreamSupport.stream(spliteratorUnknownSize(iterator, ORDERED), false);
	}

}
