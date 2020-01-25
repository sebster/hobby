package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramDocument;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramDocumentMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramDocument document;

	public TelegramDocumentMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramDocument document) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.document = requireNonNull(document, "document");
	}

	/**
	 * Information about the document file.
	 */
	public TelegramDocument getDocument() {
		return document;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformDocumentMessage(this);
	}
	
	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitDocumentMessage(this);
	}

}
