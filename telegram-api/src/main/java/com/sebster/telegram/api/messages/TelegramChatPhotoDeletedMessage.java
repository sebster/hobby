package com.sebster.telegram.api.messages;

import java.util.Date;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(doNotUseGetters = true, callSuper = true)
@SuperBuilder(toBuilder = true)
public final class TelegramChatPhotoDeletedMessage extends TelegramMessage {

	public TelegramChatPhotoDeletedMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage
	) {
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
