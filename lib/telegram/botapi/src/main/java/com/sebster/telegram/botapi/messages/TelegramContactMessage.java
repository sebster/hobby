package com.sebster.telegram.botapi.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramContact;
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
public final class TelegramContactMessage extends TelegramMessage {

	@NonNull TelegramContact contact;

	public TelegramContactMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			@NonNull TelegramContact contact
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.contact = contact;
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
