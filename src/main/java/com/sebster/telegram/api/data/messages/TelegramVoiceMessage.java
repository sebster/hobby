package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.TelegramVoice;

public class TelegramVoiceMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramVoice voice;

	public TelegramVoiceMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramVoice voice) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.voice = requireNonNull(voice, "voice");
	}

	/**
	 * Information about the voice file.
	 */
	public TelegramVoice getVoice() {
		return voice;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformVoiceMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitVoiceMessage(this);
	}

}
