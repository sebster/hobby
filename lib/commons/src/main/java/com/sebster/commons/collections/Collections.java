package com.sebster.commons.collections;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class Collections {

	public static <T> T singletonElement(Collection<? extends T> singletonCollection) {
		if (singletonCollection.size() != 1) {
			throw new IllegalStateException("Collection is not a singleton: size=" + singletonCollection.size());
		}
		return singletonCollection.stream().findFirst().orElseThrow();
	}

}
