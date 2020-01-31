package com.sebster.telegram.botapi.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramPhotoList;
import com.sebster.telegram.botapi.data.TelegramUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
@SuperBuilder(toBuilder = true)
public final class TelegramPhotoMessage extends TelegramMessage implements WithCaption {

	@NonNull TelegramPhotoList photoList;
	String caption;

	public TelegramPhotoMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			@NonNull TelegramPhotoList photoList, String caption
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.photoList = photoList;
		this.caption = caption;
	}

	@Override
	public Optional<String> getCaption() {
		return Optional.ofNullable(caption);
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
