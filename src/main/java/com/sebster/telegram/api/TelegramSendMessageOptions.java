package com.sebster.telegram.api;

import static com.sebster.telegram.api.TelegramParseMode.HTML;
import static com.sebster.telegram.api.TelegramParseMode.MARKDOWN;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

public class TelegramSendMessageOptions {

	private final Optional<TelegramParseMode> parseMode;
	private final Optional<Boolean> disableWebPagePreview;
	private final Optional<Boolean> disableNotification;

	private TelegramSendMessageOptions(Optional<TelegramParseMode> parseMode, Optional<Boolean> disableWebPagePreview,
			Optional<Boolean> disableNotification) {
		this.parseMode = parseMode;
		this.disableWebPagePreview = disableWebPagePreview;
		this.disableNotification = disableNotification;
	}

	public Optional<TelegramParseMode> getParseMode() {
		return parseMode;
	}

	public Optional<Boolean> getDisableWebPagePreview() {
		return disableWebPagePreview;
	}

	public Optional<Boolean> getDisableNotification() {
		return disableNotification;
	}

	public static TelegramSendMessageOptions defaultSendMessageOptions() {
		return withOptions().build();
	}

	public static TelegramSendMessageOptions html() {
		return withOptions().parseMode(HTML).build();
	}
	
	public static TelegramSendMessageOptions markdown() {
		return withOptions().parseMode(MARKDOWN).build();
	}
	
	public static Builder withOptions() {
		return new Builder();
	}

	public static class Builder {

		private Optional<TelegramParseMode> parseMode = Optional.empty();
		private Optional<Boolean> disableWebPagePreview = Optional.empty();
		private Optional<Boolean> disableNotification = Optional.empty();

		public Builder parseMode(TelegramParseMode parseMode) {
			this.parseMode = Optional.of(requireNonNull(parseMode, "parseMode"));
			return this;
		}

		public Builder disableWebPagePreview(boolean disableWebPagePreview) {
			this.disableWebPagePreview = Optional.of(disableWebPagePreview);
			return this;
		}

		public Builder disableNotification(boolean disableNotification) {
			this.disableNotification = Optional.of(disableNotification);
			return this;
		}

		public TelegramSendMessageOptions build() {
			return new TelegramSendMessageOptions(parseMode, disableWebPagePreview, disableNotification);
		}

	}

}
