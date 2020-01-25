package com.sebster.telegram.api;

public class TelegramServiceException extends TelegramException {

	private static final long serialVersionUID = 1L;

	private final int errorCode;
	private final String description;

	public TelegramServiceException(int errorCode, String description) {
		super(description + " (" + errorCode + ")");
		this.errorCode = errorCode;
		this.description = description;
	}

	/**
	 * Get the error code returned by the Telegram API. Its contents are subject
	 * to change in the future.
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
