package com.sebster.telegram.api.data;

public interface WithDuration {

	/**
	 * Get the duration in seconds.
	 */
	int getDuration();

	interface TelegramDurationBuilder {

		TelegramDurationBuilder duration(int duration);

		WithDuration build();

	}

}
