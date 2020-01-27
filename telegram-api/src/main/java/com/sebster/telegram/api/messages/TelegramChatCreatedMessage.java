package com.sebster.telegram.api.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramChatType;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Service message: the channel, group, or supergroup has been created.
 */
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString
public final class TelegramChatCreatedMessage extends TelegramMessage {

	@NonNull TelegramChatType chatType;

	public TelegramChatCreatedMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			@NonNull TelegramChatType chatType
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.chatType = chatType;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformChatCreatedMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitChatCreatedMessage(this);
	}

}
