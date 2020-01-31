package com.sebster.telegram.botapi.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramLocation;
import com.sebster.telegram.botapi.data.TelegramUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
@SuperBuilder(toBuilder = true)
public final class TelegramLocationMessage extends TelegramMessage {

	@NonNull TelegramLocation location;

	public TelegramLocationMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			@NonNull TelegramLocation location
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.location = location;
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
