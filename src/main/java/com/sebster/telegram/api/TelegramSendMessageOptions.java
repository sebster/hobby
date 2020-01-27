package com.sebster.telegram.api;

import static com.sebster.telegram.api.TelegramParseMode.HTML;
import static com.sebster.telegram.api.TelegramParseMode.MARKDOWN;

import java.util.Optional;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderMethodName = "withOptions")
public class TelegramSendMessageOptions {

	TelegramParseMode parseMode;
	Boolean disableWebPagePreview;
	Boolean disableNotification;

	public Optional<TelegramParseMode> getParseMode() {
		return Optional.ofNullable(parseMode);
	}

	public Optional<Boolean> getDisableWebPagePreview() {
		return Optional.ofNullable(disableWebPagePreview);
	}

	public Optional<Boolean> getDisableNotification() {
		return Optional.ofNullable(disableNotification);
	}

	public static TelegramSendMessageOptions defaultOptions() {
		return withOptions().build();
	}

	public static TelegramSendMessageOptions html() {
		return withOptions().parseMode(HTML).build();
	}

	public static TelegramSendMessageOptions markdown() {
		return withOptions().parseMode(MARKDOWN).build();
	}

}
