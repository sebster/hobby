package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.*;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramVideo;

/**
 * This object represents a video file.
 */
public final class TelegramVideoDto {

	private final String fileId;
	private final int width;
	private final int height;
	private final int duration;
	private final Optional<TelegramPhotoSizeDto> thumb;
	private final Optional<String> mimeType;
	private final Optional<Integer> fileSize;

	@JsonCreator
	public TelegramVideoDto(@JsonProperty("file_id") String fileId, @JsonProperty("width") int width,
			@JsonProperty("height") int height, @JsonProperty("duration") int duration,
			@JsonProperty("thumb") Optional<TelegramPhotoSizeDto> thumb,
			@JsonProperty("mime_type") Optional<String> mimeType,
			@JsonProperty("file_size") Optional<Integer> fileSize) {
		this.fileId = fileId;
		this.width = width;
		this.height = height;
		this.duration = duration;
		this.thumb = thumb;
		this.mimeType = mimeType;
		this.fileSize = fileSize;
	}

	/**
	 * Unique identifier for this file.
	 */
	public final String getFileId() {
		return fileId;
	}

	/**
	 * Video width as defined by sender.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Video height as defined by sender.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Duration of the video in seconds as defined by sender.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Optional. Video thumbnail.
	 */
	public Optional<TelegramPhotoSizeDto> getThumb() {
		return thumb;
	}

	/**
	 * Optional. Mime type of a file as defined by sender.
	 */
	public Optional<String> getMimeType() {
		return mimeType;
	}

	/**
	 * Optional. File size.
	 */
	public final Optional<Integer> getFileSize() {
		return fileSize;
	}

	public TelegramVideo toTelegramVideo() {
		return new TelegramVideo(fileId, width, height, duration, thumb.map(TelegramPhotoSizeDto::toTelegramPhoto),
				mimeType, fileSize);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
