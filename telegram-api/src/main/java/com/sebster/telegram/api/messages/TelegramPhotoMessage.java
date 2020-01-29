package com.sebster.telegram.api.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;
import java.util.Optional;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramPhotoList;
import com.sebster.telegram.api.data.TelegramUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
public final class TelegramPhotoMessage extends TelegramMessage implements WithCaption {

	@NonNull TelegramPhotoList photoList;
	String caption;

	@Builder(toBuilder = true)
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

	public static class TelegramPhotoMessageBuilder implements TelegramMessageBuilder {
	}

}
