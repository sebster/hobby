package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.TelegramVideo;

public class TelegramVideoMessage extends TelegramMessage implements WithCaption {

	private static final long serialVersionUID = 1L;

	private final TelegramVideo video;
	private final Optional<String> caption;

	public TelegramVideoMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramVideo video, Optional<String> caption) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.video = requireNonNull(video, "video");
		this.caption = requireNonNull(caption, "caption");
	}

	/**
	 * Information about the video file.
	 */
	public TelegramVideo getVideo() {
		return video;
	}

	@Override
	public Optional<String> getCaption() {
		return caption;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformVideoMessage(this);
	}
	
	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitVideoMessage(this);
	}

}
