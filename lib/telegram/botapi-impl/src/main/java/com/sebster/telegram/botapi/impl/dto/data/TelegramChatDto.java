package com.sebster.telegram.botapi.impl.dto.data;

import static com.sebster.telegram.botapi.data.TelegramChatType.CHANNEL;
import static com.sebster.telegram.botapi.data.TelegramChatType.GROUP;
import static com.sebster.telegram.botapi.data.TelegramChatType.PRIVATE;
import static com.sebster.telegram.botapi.data.TelegramChatType.SUPERGROUP;
import static com.sebster.telegram.botapi.data.TelegramChatType.UNKNOWN;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramChatType;
import lombok.Data;

@Data
public class TelegramChatDto {

	long id;
	String type;
	String title;
	String username;
	String firstName;
	String lastName;

	public TelegramChat toTelegramChat() {
		return new TelegramChat(id, unmarshalChatType(type), title, username, firstName, lastName);
	}

	private TelegramChatType unmarshalChatType(String chatType) {
		if (chatType == null) {
			return null;
		}
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
			return UNKNOWN;
		}
	}

}
