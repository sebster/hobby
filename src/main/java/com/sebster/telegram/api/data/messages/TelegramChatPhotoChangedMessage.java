package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramPhotoList;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramChatPhotoChangedMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramPhotoList newChatPhoto;

	public TelegramChatPhotoChangedMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramPhotoList newChatPhoto) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.newChatPhoto = requireNonNull(newChatPhoto, "newChatPhoto");
	}

	/**
	 * A chat photo was change to this value.
	 */
	public TelegramPhotoList getNewChatPhoto() {
		return newChatPhoto;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformChatPhotoChangedMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitChatPhotoChangedMessage(this);
	}

}
