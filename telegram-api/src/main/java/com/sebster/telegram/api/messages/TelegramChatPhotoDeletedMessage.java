package com.sebster.telegram.api.messages;

import java.util.Date;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

@ToString(doNotUseGetters = true, callSuper = true)
public final class TelegramChatPhotoDeletedMessage extends TelegramMessage {

	@Builder(toBuilder = true)
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

	public static class TelegramChatPhotoDeletedMessageBuilder implements TelegramMessageBuilder {
	}

}
