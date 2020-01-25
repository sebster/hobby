package com.sebster.telegram.api;

public class TelegramException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TelegramException(String message) {
		super(message);
	}

	public TelegramException(String message, Throwable cause) {
		super(message, cause);
	}

	public TelegramException(Throwable cause) {
		super(cause);
	}

}
