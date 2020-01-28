package com.sebster.telegram.api.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * The group has been migrated to a supergroup with the specified identifier, not exceeding 1e13 by absolute value.
 */
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
public final class TelegramChatMigratedToSupergroupMessage extends TelegramMessage {

	long supergroupChatId;

	public TelegramChatMigratedToSupergroupMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			long supergroupChatId
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.supergroupChatId = supergroupChatId;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformChatMigratedToSupergroupMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitChatMigratedToSupergroupMessage(this);
	}

}
