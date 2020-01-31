package com.sebster.telegram.botapi.data;

public interface WithDimension {

	/**
	 * Get the width in pixels.
	 */
	int getWidth();

	/**
	 * Get the height in pixels.
	 */
	int getHeight();

	interface TelegramDimensionBuilder {

		TelegramDimensionBuilder width(int width);

		TelegramDimensionBuilder height(int height);

		WithDimension build();

	}

}
