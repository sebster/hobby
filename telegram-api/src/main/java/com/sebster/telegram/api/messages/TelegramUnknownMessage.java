package com.sebster.telegram.api.messages;

import java.util.Date;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.NonNull;
import lombok.ToString;

@ToString(doNotUseGetters = true, callSuper = true)
public final class TelegramUnknownMessage extends TelegramMessage {

	public TelegramUnknownMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformUnknownMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitUnknownMessage(this);
	}

}
