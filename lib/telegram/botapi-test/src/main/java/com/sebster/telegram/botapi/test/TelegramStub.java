package com.sebster.telegram.botapi.test;

import java.time.Duration;
import java.util.Optional;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramFileLink;
import com.sebster.telegram.botapi.data.TelegramUser;
import com.sebster.telegram.botapi.messages.TelegramMessage;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TelegramStub {

	private final @NonNull TelegramServiceStub telegramServiceStub;

	public void setMe(@NonNull TelegramUser user) {
		telegramServiceStub.setMe(user);
	}

	public void createChat(@NonNull TelegramChat chat) {
		telegramServiceStub.registerChat(chat);
	}

	public void createFileLink(@NonNull TelegramFileLink fileLink) {
		telegramServiceStub.registerFileLink(fileLink);
	}

	public void sendMessage(@NonNull TelegramMessage message) {
		telegramServiceStub.receiveMessage(message);
	}

	public <M extends TelegramMessage> Optional<M> receiveMessage(@NonNull Class<M> messageClass) {
		TelegramMessage message = telegramServiceStub.pollSentMessage().orElse(null);
		return Optional.ofNullable(messageClass.cast(message));
	}

	public <M extends TelegramMessage> Optional<M> receiveMessage(@NonNull Class<M> messageClass, @NonNull Duration timeout) {
		TelegramMessage message = telegramServiceStub.pollSentMessage().orElse(null);
		return Optional.ofNullable(messageClass.cast(message));
	}

}
