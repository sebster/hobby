package com.sebster.weereld.hobbes.utils;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor(access = PRIVATE)
public class StringUtils {

	public static String formatIfNotNull(Object value, String format) {
		return value != null ? format(format, value) : "";
	}

	public static String formatIfPresent(Optional<?> optional, String format) {
		return optional.isPresent() ? String.format(format, optional.get()) : "";
	}

}
