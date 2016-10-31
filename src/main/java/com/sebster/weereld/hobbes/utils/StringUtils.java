package com.sebster.weereld.hobbes.utils;

import java.util.Optional;

public class StringUtils {
	
	private StringUtils() {
	}

	public static String onlyIfPresent(Optional<?> optional, String format) {
		return optional.isPresent() ? String.format(format, optional.get()) : "";
	}

}
