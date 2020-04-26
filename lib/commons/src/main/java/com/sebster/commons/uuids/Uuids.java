package com.sebster.commons.uuids;

import static lombok.AccessLevel.PRIVATE;

import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(access = PRIVATE)
public class Uuids {

	public static Pattern UUID_PATTERN =
			Pattern.compile("\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}");

	public static boolean isUuid(@NonNull String string) {
		return UUID_PATTERN.matcher(string).matches();
	}

}
