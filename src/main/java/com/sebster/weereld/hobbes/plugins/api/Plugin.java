package com.sebster.weereld.hobbes.plugins.api;

import com.sebster.telegram.api.data.messages.TelegramMessage;

public interface Plugin {
	
	void receiveMessage(TelegramMessage telegramMessage);
	
}
