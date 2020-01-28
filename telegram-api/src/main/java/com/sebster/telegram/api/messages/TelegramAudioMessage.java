package com.sebster.telegram.api.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

import com.sebster.telegram.api.data.TelegramAudio;
import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
public final class TelegramAudioMessage extends TelegramMessage {

	@NonNull TelegramAudio audio;

	public TelegramAudioMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			@NonNull TelegramAudio audio
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.audio = audio;
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
