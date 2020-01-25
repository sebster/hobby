package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramPhotoList;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramPhotoMessage extends TelegramMessage implements WithCaption {

	private static final long serialVersionUID = 1L;

	private final TelegramPhotoList photoList;
	private final Optional<String> caption;

	public TelegramPhotoMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramPhotoList photoList, Optional<String> caption) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.photoList = requireNonNull(photoList, "photoList");
		this.caption = requireNonNull(caption, "caption");
	}

	/**
	 * Get information about all the sizes of the photo.
	 */
	public TelegramPhotoList getPhotoList() {
		return photoList;
	}

	@Override
	public Optional<String> getCaption() {
		return caption;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformPhotoMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitPhotoMessage(this);
	}

}
