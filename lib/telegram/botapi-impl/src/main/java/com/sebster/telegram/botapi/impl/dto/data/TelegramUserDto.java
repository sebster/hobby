package com.sebster.telegram.botapi.impl.dto.data;

import com.sebster.telegram.botapi.data.TelegramUser;
import lombok.Data;

@Data
public class TelegramUserDto {

	int id;
	boolean isBot;
	String firstName;
	String lastName;
	String username;
	String languageCode;

	public TelegramUser toTelegramUser() {
		return new TelegramUser(id, isBot, firstName, lastName, username, languageCode);
	}

}
