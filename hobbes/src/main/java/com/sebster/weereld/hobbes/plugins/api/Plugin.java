package com.sebster.weereld.hobbes.plugins.api;

import com.sebster.telegram.api.messages.TelegramMessage;

@FunctionalInterface
public interface Plugin {

	void receiveMessage(TelegramMessage telegramMessage);

}
