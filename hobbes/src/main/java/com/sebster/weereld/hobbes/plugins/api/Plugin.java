package com.sebster.weereld.hobbes.plugins.api;

import com.sebster.telegram.api.messages.TelegramMessage;

public interface Plugin {

	String getName();

	String getDescription();

	void receiveMessage(TelegramMessage telegramMessage);

}
