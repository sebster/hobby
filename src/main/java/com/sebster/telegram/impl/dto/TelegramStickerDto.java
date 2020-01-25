package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramSticker;

/**
 * This object represents a sticker.
 */
public final class TelegramStickerDto {

	private final String fileId;
	private final int width;
	private final int height;
	private final Optional<TelegramPhotoSizeDto> thumb;
	private final Optional<Integer> fileSize;

	@JsonCreator
	public TelegramStickerDto(@JsonProperty("file_id") String fileId, @JsonProperty("width") int width,
			@JsonProperty("height") int height, @JsonProperty("thumb") Optional<TelegramPhotoSizeDto> thumb,
			@JsonProperty("file_size") Optional<Integer> fileSize) {
		this.fileId = fileId;
		this.width = width;
		this.height = height;
		this.thumb = thumb;
		this.fileSize = fileSize;
	}

	/**
	 * Unique identifier for this file.
	 */
	public final String getFileId() {
		return fileId;
	}

	/**
	 * Sticker width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sticker height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Optional. Sticker thumbnail in .webp or .jpg format.
	 */
	public Optional<TelegramPhotoSizeDto> getThumb() {
		return thumb;
	}

	/**
	 * Optional. File size.
	 */
	public final Optional<Integer> getFileSize() {
		return fileSize;
	}

	public TelegramSticker toTelegramSticker() {
		return new TelegramSticker(fileId, width, height, thumb.map(TelegramPhotoSizeDto::toTelegramPhoto), fileSize);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
