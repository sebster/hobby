package com.sebster.telegram.botapi.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represents a sticker.
 */
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class TelegramSticker implements TelegramFile, WithDimension, WithThumbnail {

	@NonNull String fileId;
	@NonNull String fileUniqueId;
	int width;
	int height;
	TelegramPhoto thumbnail;
	Integer fileSize;

	/**
	 * Sticker width.
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Sticker height.
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Optional. Sticker thumbnail in .webp or .jpg format.
	 */
	@Override
	public Optional<TelegramPhoto> getThumbnail() {
		return Optional.ofNullable(thumbnail);
	}

	@Override
	public Optional<Integer> getFileSize() {
		return Optional.ofNullable(fileSize);
	}

	// TODO:
	//	is_animated 	Boolean 	True, if the sticker is animated
	//	emoji 	String 	Optional. Emoji associated with the sticker
	//	set_name 	String 	Optional. Name of the sticker set to which the sticker belongs
	//	mask_position 	MaskPosition 	Optional. For mask stickers, the position where the mask should be placed
	//	file_size 	Integer 	Optional. File size

	public static class TelegramStickerBuilder implements TelegramFileBuilder, TelegramDimensionBuilder, TelegramThumbnailBuilder {
	}

}
