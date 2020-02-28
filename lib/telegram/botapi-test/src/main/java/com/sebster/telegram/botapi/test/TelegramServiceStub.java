package com.sebster.telegram.botapi.test;

import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.sebster.commons.clock.ClockService;
import com.sebster.telegram.botapi.TelegramSendMessageOptions;
import com.sebster.telegram.botapi.TelegramService;
import com.sebster.telegram.botapi.TelegramUpdate;
import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramFileLink;
import com.sebster.telegram.botapi.data.TelegramUser;
import com.sebster.telegram.botapi.messages.TelegramMessage;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import lombok.NonNull;

public class TelegramServiceStub implements TelegramService {

	private final @NonNull ClockService clockService;

	private final AtomicReference<TelegramUser> me = new AtomicReference<>();
	private final List<TelegramChat> chats = new CopyOnWriteArrayList<>();
	private final List<TelegramFileLink> fileLinks = new CopyOnWriteArrayList<>();

	private final AtomicInteger nextMessageId = new AtomicInteger(0);
	private final BlockingQueue<TelegramMessage> sentMessages = new LinkedBlockingQueue<>();

	private final AtomicInteger nextUpdateId = new AtomicInteger(0);
	private final BlockingQueue<TelegramUpdate> updates = new LinkedBlockingQueue<>();

	public TelegramServiceStub(@NonNull ClockService clockService) {
		this.clockService = clockService;
	}

	@Override
	public TelegramUser getMe() {
		return requireNonNull(me.get(), "Telegram user must be set");
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset, int limit, @NonNull Duration timeout) {
		List<TelegramUpdate> results = new ArrayList<>();
		try {
			TelegramUpdate update = updates.poll(timeout.toMillis(), MILLISECONDS);
			if (update != null) {
				results.add(update);
				updates.drainTo(results, limit - 1);
			}
			return results;
		} catch (InterruptedException e) {
			currentThread().interrupt();
			throw new RuntimeException("Interrupted waiting for updates", e);
		}
	}

	@Override
	public TelegramFileLink getFileLink(@NonNull String fileId) {
		return fileLinks.stream()
				.filter(fileLink -> Objects.equals(fileLink.getFileId(), fileId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unknown file id: " + fileId));
	}

	@Override
	public TelegramMessage sendMessage(long chatId, @NonNull TelegramSendMessageOptions options, @NonNull String text) {
		return sendMessage(
				TelegramTextMessage.builder()
						.messageId(nextMessageId.getAndIncrement())
						.date(clockService.date())
						.chat(lookupChat(chatId))
						.from(getMe())
						.text(text)
						.build()
		);
	}

	@Override
	public TelegramTextMessage sendMessage(String channel, @NonNull TelegramSendMessageOptions options, @NonNull String text) {
		return sendMessage(
				TelegramTextMessage.builder()
						.messageId(nextMessageId.getAndIncrement())
						.chat(lookupChat(channel))
						.text(text)
						.date(clockService.date())
						.build()
		);
	}

	protected <T extends TelegramMessage> T sendMessage(T message) {
		sentMessages.add(message);
		return message;
	}

	TelegramChat lookupChat(long chatId) {
		return chats.stream()
				.filter(chat -> chat.getId() == chatId)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unknown chat: " + chatId));
	}

	TelegramChat lookupChat(@NonNull String channel) {
		return chats.stream()
				.filter(chat -> Objects.equals(chat.getUsername().orElse(null), channel))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unknown channel: " + channel));
	}

	void setMe(TelegramUser me) {
		this.me.set(me);
	}

	void registerChat(@NonNull TelegramChat chat) {
		chats.add(chat);
	}

	void registerFileLink(@NonNull TelegramFileLink fileLink) {
		fileLinks.add(fileLink);
	}

	void receiveMessage(@NonNull TelegramMessage message) {
		updates.add(
				TelegramUpdate.builder()
						.updateId(nextUpdateId.getAndIncrement())
						.message(message)
						.build()
		);
	}

	Optional<TelegramMessage> pollSentMessage() {
		return Optional.ofNullable(sentMessages.poll());
	}

	Optional<TelegramMessage> pollSentMessage(@NonNull Duration timeout) {
		try {
			return Optional.ofNullable(sentMessages.poll(timeout.toMillis(), MILLISECONDS));
		} catch (InterruptedException e) {
			currentThread().interrupt();
			throw new RuntimeException("Interrupted waiting for messages to be sent", e);
		}
	}

}
