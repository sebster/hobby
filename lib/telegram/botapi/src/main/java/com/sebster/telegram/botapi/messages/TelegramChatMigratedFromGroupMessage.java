package com.sebster.telegram.botapi.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The supergroup has been migrated from a group with the specified identifier, not exceeding 1e13 by absolute value.
 */
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
@SuperBuilder(toBuilder = true)
public final class TelegramChatMigratedFromGroupMessage extends TelegramMessage {

	long groupChatId;

	public TelegramChatMigratedFromGroupMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			long groupChatId
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.groupChatId = groupChatId;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> visitor) {
		return visitor.transformChatMigratedFromGroupMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitChatMigratedFromGroupMessage(this);
	}

}
