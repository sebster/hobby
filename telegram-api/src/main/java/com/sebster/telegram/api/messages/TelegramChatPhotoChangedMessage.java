package com.sebster.telegram.api.messages;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;

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
public class TelegramChatPhotoChangedMessage extends TelegramMessage {

	@NonNull TelegramPhotoList newChatPhoto;

	@Builder(toBuilder = true)
	public TelegramChatPhotoChangedMessage(
			int messageId, TelegramUser from, @NonNull Date date, @NonNull TelegramChat chat,
			TelegramUser forwardFrom, Date forwardDate, TelegramMessage replyToMessage,
			@NonNull TelegramPhotoList newChatPhoto
	) {
		super(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		this.newChatPhoto = newChatPhoto;
	}

	@Override
	public <T> T accept(TelegramMessageTransformer<T> transformer) {
		return transformer.transformChatPhotoChangedMessage(this);
	}

	@Override
	public void accept(TelegramMessageVisitor visitor) {
		visitor.visitChatPhotoChangedMessage(this);
	}

	public static class TelegramChatPhotoChangedMessageBuilder implements TelegramMessageBuilder {
	}

}
