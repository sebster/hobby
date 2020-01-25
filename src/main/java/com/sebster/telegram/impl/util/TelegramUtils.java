package com.sebster.telegram.impl.util;

import static com.sebster.telegram.api.data.TelegramChatType.*;

import java.util.Date;

import com.sebster.telegram.api.TelegramException;
import com.sebster.telegram.api.TelegramParseMode;
import com.sebster.telegram.api.data.TelegramChatType;

public class TelegramUtils {

	private TelegramUtils() {
		// Utility class.
	}

	public static Date unixTimeToDate(int unixTime) {
		return new Date(unixTime * 1000L);
	}

	public static TelegramChatType toChatType(String chatType) {
		switch (chatType) {
		case "private":
			return PRIVATE;
		case "group":
			return GROUP;
		case "supergroup":
			return SUPERGROUP;
		case "channel":
			return CHANNEL;
		default:
			throw new TelegramException("Invalid chat type: " + chatType);
		}
	}

	public static String toString(TelegramParseMode parseMode) {
		switch (parseMode) {
		case HTML:
			return "HTML";
		case MARKDOWN:
			return "Markdown";
		default:
			throw new TelegramException("Invalid parse mode: " + parseMode);
		}
	}

}
