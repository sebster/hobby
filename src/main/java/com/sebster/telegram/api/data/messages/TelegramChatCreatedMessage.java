package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramChatType;
import com.sebster.telegram.api.data.TelegramUser;

/**
 * Service message: the channel, group, or supergroup has been created.
 */
public class TelegramChatCreatedMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramChatType chatType;

	public TelegramChatCreatedMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramChatType chatType) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.chatType = requireNonNull(chatType, "chatType");
	}

	public TelegramChatType getChatType() {
		return chatType;
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
