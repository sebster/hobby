package com.sebster.telegram.botapi.data;

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
