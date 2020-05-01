package com.sebster.commons.strings;

import static com.sebster.commons.streams.Streams.stream;
import static java.util.stream.Collectors.joining;
import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = PRIVATE)
public class Strings {

	public static boolean isBlank(CharSequence charSequence) {
		return charSequence == null || charSequence.chars().allMatch(Character::isWhitespace);
	}

	public static boolean isNotBlank(CharSequence charSequence) {
		return !isBlank(charSequence);
	}

	public static boolean containsIgnoreCase(@NonNull String string, @NonNull String substring) {
		return string.toLowerCase().contains(substring.toLowerCase());
	}

	public static boolean startsWithIgnoreCase(@NonNull String string, @NonNull String prefix) {
		return string.toLowerCase().startsWith(prefix.toLowerCase());
	}

	public static String join(@NonNull CharSequence delimiter, @NonNull Iterable<?> elements) {
		return stream(elements).map(String::valueOf).collect(joining(delimiter));
	}

}
