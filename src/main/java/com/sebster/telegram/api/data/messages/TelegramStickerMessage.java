package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramSticker;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramStickerMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramSticker sticker;

	public TelegramStickerMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramSticker sticker) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.sticker = requireNonNull(sticker, "sticker");
	}

	/**
	 * Information about the sticker file.
	 */
	public TelegramSticker getSticker() {
		return sticker;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformStickerMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitStickerMessage(this);
	}

}
