package com.sebster.telegram.botapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum TelegramEmoji {

	CAKE("cake", "\uD83C\uDF70");

	private final @NonNull String code;
	private final @NonNull String unicode;

	private static final Map<String, TelegramEmoji> BY_CODE;

	static {
		BY_CODE = new HashMap<>();
		for (TelegramEmoji emoji : values()) {
			BY_CODE.put(emoji.getCode(), emoji);
		}
	}

	/**
	 * Replace all occurrences of :emoji_name: with the unicode of the emoji. If the emoji with the specified name is not found, the
	 * text is left intact.
	 */
	public static String expand(@NonNull String text) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		while (true) {
			int emojiNameStart = text.indexOf(':', index);
			if (emojiNameStart < 0) {
				break;
			}

			int emojiNameEnd = text.indexOf(':', emojiNameStart + 1);
			if (emojiNameEnd < 0) {
				break;
			}

			sb.append(text, index, emojiNameStart);

			String emojiName = text.substring(emojiNameStart + 1, emojiNameEnd);
			sb.append(toUnicode(emojiName).orElseGet(() -> ":" + emojiName + ":"));
			index = emojiNameEnd + 1;
		}
		sb.append(text, index, text.length());
		return sb.toString();
	}

	public static Optional<TelegramEmoji> byName(@NonNull String emojiName) {
		return Optional.ofNullable(BY_CODE.get(emojiName));
	}

	public static Optional<String> toUnicode(@NonNull String emojiName) {
		return byName(emojiName).map(TelegramEmoji::getUnicode);
	}

}
