package com.sebster.telegram.botapi.messages;

import java.util.Date;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramUser;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(doNotUseGetters = true, callSuper = true)
@SuperBuilder(toBuilder = true)
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
