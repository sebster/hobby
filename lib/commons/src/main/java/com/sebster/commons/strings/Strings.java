package com.sebster.commons.strings;

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
		return string.toLowerCase().contains(substring);
	}

	public static boolean startsWithIgnoreCase(@NonNull String string, @NonNull String prefix) {
		return string.toLowerCase().startsWith(prefix.toLowerCase());
	}

}
