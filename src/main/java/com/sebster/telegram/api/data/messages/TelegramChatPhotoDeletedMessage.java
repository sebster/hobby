package com.sebster.telegram.api.data.messages;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramChatPhotoDeletedMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	public TelegramChatPhotoDeletedMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformChatPhotoDeletedMessage(this);
	}
	
	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitChatPhotoDeletedMessage(this);
	}

}
