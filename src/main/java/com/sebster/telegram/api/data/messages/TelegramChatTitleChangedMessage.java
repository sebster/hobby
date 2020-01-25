package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramChatTitleChangedMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final String newChatTitle;

	public TelegramChatTitleChangedMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			String newChatTitle) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.newChatTitle = requireNonNull(newChatTitle, "newChatTitle");
	}

	/**
	 * A chat title was changed to this value.
	 */
	public String getNewChatTitle() {
		return newChatTitle;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformChatTitleChangedMessage(this);
	}
	
	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitChatTitleChangedMessage(this);
	}

}
