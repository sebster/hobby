package com.sebster.telegram.api.data;

import java.util.Optional;

import lombok.Value;

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 */
@Value
public class TelegramPhoto implements TelegramFile, WithDimension {

	String fileId;
	String fileUniqueId;
	int width;
	int height;
	Integer fileSize;

	/**
	 * Photo width.
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Photo height.
	 */
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Optional<Integer> getFileSize() {
		return Optional.ofNullable(fileSize);
	}

}
