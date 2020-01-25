package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramContact;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramContactMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramContact contact;

	public TelegramContactMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramContact contact) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.contact = requireNonNull(contact, "contact");
	}

	/**
	 * Information about the shared contact.
	 */
	public TelegramContact getContact() {
		return contact;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformContactMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitContactMessage(this);
	}

}
