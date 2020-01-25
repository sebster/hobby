package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramUserLeftChatMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramUser user;

	public TelegramUserLeftChatMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramUser user) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.user = requireNonNull(user, "user");
	}

	/**
	 * A member was removed from the group, information about them (this member
	 * may be bot itself).
	 */
	public TelegramUser getUser() {
		return user;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformUserLeftChatMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitUserLeftChatMessage(this);
	}

}
