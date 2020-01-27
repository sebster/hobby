package com.sebster.telegram.api;

import static lombok.AccessLevel.PRIVATE;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TelegramServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	int errorCode;
	@NonNull String description;

	public TelegramServiceException(int errorCode, @NonNull String description) {
		super(description + " (" + errorCode + ")");
		this.errorCode = errorCode;
		this.description = description;
	}

	/**
	 * Get the error code returned by the Telegram API. Its contents are subject to change in the future.
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * The explanation of the error.
	 */
	public String getDescription() {
		return description;
	}

}
