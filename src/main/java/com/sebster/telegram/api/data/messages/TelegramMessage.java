package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;

/**
 * This object represents a message.
 */
public abstract class TelegramMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int messageId;
	private final Optional<TelegramUser> from;
	private final Date date;
	private final TelegramChat chat;
	private final Optional<TelegramUser> forwardFrom;
	private final Optional<Date> forwardDate;
	private final Optional<TelegramMessage> replyToMessage;

	protected TelegramMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage) {
		this.messageId = messageId;
		this.from = requireNonNull(from, "from");
		this.date = requireNonNull(date, "date");
		this.chat = requireNonNull(chat, "chat");
		this.forwardFrom = requireNonNull(forwardFrom, "forwardFrom");
		this.forwardDate = requireNonNull(forwardDate, "forwardDate");
		this.replyToMessage = requireNonNull(replyToMessage, "replyToMessage");
	}

	/**
	 * Unique message identifier
	 */
	public int getMessageId() {
		return messageId;
	}

	/**
	 * Optional. Sender, can be empty for messages sent to channels
	 */
	public Optional<TelegramUser> getFrom() {
		return from;
	}

	/**
	 * Date the message was sent in Unix time
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Conversation the message belongs to
	 */
	public TelegramChat getChat() {
		return chat;
	}

	/**
	 * Optional. For forwarded messages, sender of the original message
	 */
	public Optional<TelegramUser> getForwardFrom() {
		return forwardFrom;
	}

	/**
	 * Optional. For forwarded messages, date the original message was sent in
	 * Unix time
	 */
	public Optional<Date> getForwardDate() {
		return forwardDate;
	}

	/**
	 * Optional. For replies, the original message. Note that the Message object
	 * in this field will not contain further reply_to_message fields even if it
	 * itself is a reply.
	 */
	public Optional<TelegramMessage> getReplyToMessage() {
		return replyToMessage;
	}

	public abstract <T> T accept(TelegramMessageTransformer<T> transformer);

	public abstract void accept(TelegramMessageVisitor visitor);

	@Override
	public final boolean equals(Object obj) {
		return reflectionEquals(this, obj, false);
	}

	@Override
	public final int hashCode() {
		return reflectionHashCode(this, false);
	}

	@Override
	public final String toString() {
		return reflectionToString(this);
	}

}
