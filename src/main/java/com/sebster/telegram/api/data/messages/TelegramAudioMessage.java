package com.sebster.telegram.api.data.messages;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramAudio;
import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;

public class TelegramAudioMessage extends TelegramMessage {

	private static final long serialVersionUID = 1L;

	private final TelegramAudio audio;

	public TelegramAudioMessage(int messageId, Optional<TelegramUser> from, Date date, TelegramChat chat,
			Optional<TelegramUser> forwardFrom, Optional<Date> forwardDate, Optional<TelegramMessage> replyToMessage,
			TelegramAudio audio) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.audio = requireNonNull(audio, "audio");
	}

	/**
	 * Information about the audio file.
	 */
	public TelegramAudio getAudio() {
		return audio;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformAudioMessage(this);
	}
	
	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitAudioMessage(this);
	}

}
