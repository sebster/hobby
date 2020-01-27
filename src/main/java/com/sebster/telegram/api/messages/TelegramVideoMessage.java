package com.sebster.telegram.api.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.TelegramVideo;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString
public final class TelegramVideoMessage extends TelegramMessage implements WithCaption {

	@NonNull TelegramVideo video;
	String caption;

	public TelegramVideoMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			@NonNull TelegramVideo video, String caption
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.video = video;
		this.caption = caption;
	}

	@Override
	public Optional<String> getCaption() {
		return Optional.ofNullable(caption);
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
