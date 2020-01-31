package com.sebster.telegram.botapi.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 */
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
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

	public static class TelegramPhotoBuilder implements TelegramFileBuilder, TelegramDimensionBuilder {
	}

}
