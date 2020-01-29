package com.sebster.telegram.api.messages;

import static lombok.AccessLevel.PROTECTED;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * This object represents a message.
 */
@AllArgsConstructor(access = PROTECTED)
@FieldDefaults(level = PROTECTED, makeFinal = true)
@EqualsAndHashCode(of = "messageId")
@ToString(doNotUseGetters = true)
public abstract class TelegramMessage {

	int messageId;
	TelegramUser from;
	@NonNull Date date;
	@NonNull TelegramChat chat;
	TelegramUser forwardFrom;
	Date forwardDate;
	TelegramMessage replyToMessage;

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
		return Optional.ofNullable(from);
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
		return Optional.ofNullable(forwardFrom);
	}

	/**
	 * Optional. For forwarded messages, date the original message was sent in Unix time
	 */
	public Optional<Date> getForwardDate() {
		return Optional.ofNullable(forwardDate);
	}

	/**
	 * Optional. For replies, the original message. Note that the Message object in this field will not contain further
	 * reply_to_message fields even if it itself is a reply.
	 */
	public Optional<TelegramMessage> getReplyToMessage() {
		return Optional.ofNullable(replyToMessage);
	}

	public abstract <T> T accept(TelegramMessageTransformer<T> transformer);

	public abstract void accept(TelegramMessageVisitor visitor);

	public interface TelegramMessageBuilder {

		TelegramMessageBuilder messageId(int messageId);

		TelegramMessageBuilder from(TelegramUser from);

		TelegramMessageBuilder date(@NonNull Date date);

		TelegramMessageBuilder chat(@NonNull TelegramChat chat);

		TelegramMessageBuilder forwardFrom(TelegramUser forwardFrom);

		TelegramMessageBuilder forwardDate(Date forwardDate);

		TelegramMessageBuilder replyToMessage(TelegramMessage replyToMessage);

		TelegramMessage build();

	}

}
