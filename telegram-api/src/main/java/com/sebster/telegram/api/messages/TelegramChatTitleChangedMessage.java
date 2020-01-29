package com.sebster.telegram.api.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
public final class TelegramChatTitleChangedMessage extends TelegramMessage {

	@NonNull String newChatTitle;

	@Builder(toBuilder = true)
	public TelegramChatTitleChangedMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			@NonNull String newChatTitle
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.newChatTitle = newChatTitle;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformChatTitleChangedMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitChatTitleChangedMessage(this);
	}

	public static class TelegramChatTitleChangedMessageBuilder implements TelegramMessageBuilder {
	}

}
