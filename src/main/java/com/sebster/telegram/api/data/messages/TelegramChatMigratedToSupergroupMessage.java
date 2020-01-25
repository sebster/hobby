package com.sebster.telegram.api.data.messages;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;

/**
 * The group has been migrated to a supergroup with the specified identifier,
 * not exceeding 1e13 by absolute value.
 */
public class TelegramChatMigratedToSupergroupMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final long supergroupChatId;

	public TelegramChatMigratedToSupergroupMessage(int messageId, Optional<TelegramUser> from, Date date,
			TelegramChat chat, Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate,
			Optional<TelegramMessage> replyToMessage, long supergroupChatId) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.supergroupChatId = supergroupChatId;
	}

	public long getSupergroupChatId() {
		return supergroupChatId;
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
