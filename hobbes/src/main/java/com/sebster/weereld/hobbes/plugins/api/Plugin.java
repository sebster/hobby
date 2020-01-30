package com.sebster.weereld.hobbes.plugins.api;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.messages.TelegramMessage;

public interface Plugin {

	String getName();

	String getDescription();

	void showHelp(TelegramChat telegramChat);

	void receiveMessage(TelegramMessage telegramMessage);

}
