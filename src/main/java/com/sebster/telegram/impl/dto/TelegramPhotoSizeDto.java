package com.sebster.telegram.impl.dto;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramPhoto;
import com.sebster.telegram.api.data.TelegramPhotoList;

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 */
public final class TelegramPhotoSizeDto {

	private final String fileId;
	private final int width;
	private final int height;
	private final Optional<Integer> fileSize;

	@JsonCreator
	public TelegramPhotoSizeDto(@JsonProperty("file_id") String fileId, @JsonProperty("width") int width,
			@JsonProperty("height") int height, @JsonProperty("file_size") Optional<Integer> fileSize) {
		this.fileId = fileId;
		this.width = width;
		this.height = height;
		this.fileSize = fileSize;
	}

	/**
	 * Unique identifier for this file.
	 */
	public final String getFileId() {
		return fileId;
	}

	/**
	 * Photo width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Photo height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Optional. File size.
	 */
	public final Optional<Integer> getFileSize() {
		return fileSize;
	}

	public TelegramPhoto toTelegramPhoto() {
		return new TelegramPhoto(fileId, width, height, fileSize);
	}

	public static TelegramPhotoList toTelegramPhotoList(List<TelegramPhotoSizeDto> photos) {
		return new TelegramPhotoList(photos.stream().map(photo -> photo.toTelegramPhoto()).collect(toList()));
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
