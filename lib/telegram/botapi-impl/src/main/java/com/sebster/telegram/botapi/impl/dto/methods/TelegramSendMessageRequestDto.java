package com.sebster.telegram.botapi.impl.dto.methods;

import com.sebster.telegram.botapi.TelegramParseMode;
import com.sebster.telegram.botapi.TelegramSendMessageOptions;
import lombok.NonNull;
import lombok.Value;

@Value
public final class TelegramSendMessageRequestDto {

	@NonNull Object chatId;
	@NonNull String text;
	String parseMode;
	Boolean disableWebPagePreview;
	Boolean disableNotification;

	public TelegramSendMessageRequestDto(
			@NonNull Object chatId, @NonNull String text, @NonNull TelegramSendMessageOptions options
	) {
		this.chatId = chatId;
		this.text = text;
		this.parseMode = options.getParseMode().map(this::marshalParseMode).orElse(null);
		this.disableWebPagePreview = options.getDisableWebPagePreview().orElse(null);
		this.disableNotification = options.getDisableNotification().orElse(null);
	}

	private String marshalParseMode(TelegramParseMode parseMode) {
		switch (parseMode) {
		case HTML:
			return "HTML";
		case MARKDOWN:
			return "Markdown";
		default:
			throw new IllegalArgumentException("Invalid parse mode: " + parseMode);
		}
	}

}
