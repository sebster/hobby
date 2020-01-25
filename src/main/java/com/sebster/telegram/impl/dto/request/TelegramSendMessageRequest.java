package com.sebster.telegram.impl.dto.request;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import com.sebster.telegram.api.TelegramParseMode;
import com.sebster.telegram.impl.util.TelegramUtils;

public final class TelegramSendMessageRequest {

	private final Object chatId;
	private final String text;
	private final String parseMode;
	private final Boolean disableWebPagePreview;
	private final Boolean disableNotification;

	public TelegramSendMessageRequest(Object chatId, String text, TelegramParseMode parseMode, Boolean disableWebPagePreview,
			Boolean disableNotification) {
		this.chatId = requireNonNull(chatId, "chatId");
		this.text = requireNonNull(text, "text");
		this.parseMode = parseMode != null ? TelegramUtils.toString(parseMode) : null;
		this.disableWebPagePreview = disableWebPagePreview;
		this.disableNotification = disableNotification;
	}

	public Object getChatId() {
		return chatId;
	}

	public String getText() {
		return text;
	}

	public String getParseMode() {
		return parseMode;
	}

	public Boolean getDisableWebPagePreview() {
		return disableWebPagePreview;
	}

	public Boolean getDisableNotification() {
		return disableNotification;
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
