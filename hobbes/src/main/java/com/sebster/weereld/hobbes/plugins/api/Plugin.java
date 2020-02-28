package com.sebster.weereld.hobbes.plugins.api;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramMessage;

public interface Plugin {

	String getName();

	String getDescription();

	void showHelp(TelegramChat chat);

	void receiveMessage(TelegramMessage message);

	void enable();

	void disable();

	boolean isEnabled();

}
