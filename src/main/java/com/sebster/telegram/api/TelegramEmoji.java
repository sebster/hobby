package com.sebster.telegram.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TelegramEmoji {

	CAKE("cake", "\uD83C\uDF70");

	private final String code;
	private final String unicode;

	private static final Map<String, TelegramEmoji> BY_CODE;

	static {
		BY_CODE = new HashMap<>();
		for (TelegramEmoji emoji : values()) {
			BY_CODE.put(emoji.code(), emoji);
		}
	}

	private TelegramEmoji(String code, String unicode) {
		this.code = code;
		this.unicode = unicode;
	}

	public String code() {
		return code;
	}

	public String unicode() {
		return unicode;
	}

	public static String expand(String text) {
		StringBuilder sb = new StringBuilder();
		int lastI = 0;
		while (true) {
			int i = text.indexOf(':', lastI);
			if (i < 0) {
				break;
			}
			int j = text.indexOf(':', i + 1);
			if (j < 0) {
				break;
			}
			String name = text.substring(i + 1, j);
			Optional<TelegramEmoji> emoji = find(name);
			if (emoji.isPresent()) {
				sb.append(text.substring(lastI, i));
				sb.append(emoji.get().unicode());
				lastI = j + 1;
			} else {
				sb.append(text.substring(lastI, j));
				lastI = j;
			}
		}
		sb.append(text.substring(lastI));
		return sb.toString();
	}

	private static Optional<TelegramEmoji> find(String name) {
		return Optional.ofNullable(BY_CODE.get(name));
	}

}
