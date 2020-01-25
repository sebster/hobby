package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramLocation;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramLocationMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramLocation location;

	public TelegramLocationMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramLocation location) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.location = requireNonNull(location, "location");
	}

	/**
	 * Information about the shared location.
	 */
	public TelegramLocation getLocation() {
		return location;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformLocationMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitLocationMessage(this);
	}

}
