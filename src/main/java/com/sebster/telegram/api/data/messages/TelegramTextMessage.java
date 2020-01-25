package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramTextMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final String text;

	public TelegramTextMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			String text) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.text = requireNonNull(text, "text");
	}

	/**
	 * The actual UTF-8 text of the message.
	 */
	public String getText() {
		return text;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformTextMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitTextMessage(this);
	}

}
