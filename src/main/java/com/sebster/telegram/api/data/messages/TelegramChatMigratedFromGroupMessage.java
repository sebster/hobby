package com.sebster.telegram.api.data.messages;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;

/**
 * The supergroup has been migrated from a group with the specified identifier,
 * not exceeding 1e13 by absolute value.
 */
public class TelegramChatMigratedFromGroupMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final long groupChatId;

	public TelegramChatMigratedFromGroupMessage(int messageId, Optional<TelegramUser> from, Date date,
			TelegramChat chat, Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate,
			Optional<TelegramMessage> replyToMessage, long groupChatId) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.groupChatId = groupChatId;
	}

	public long getGroupChatId() {
		return groupChatId;
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
