package com.sebster.telegram.api.data;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represents a video file.
 */
@Value
@EqualsAndHashCode(of = "fileUniqueId")
public final class TelegramVideo implements TelegramFile, WithDimension, WithDuration, WithThumbnail, WithMimeType {

	@NonNull String fileId;
	@NonNull String fileUniqueId;
	int width;
	int height;
	int duration;
	TelegramPhoto thumbnail;
	String mimeType;
	Integer fileSize;

	/**
	 * Video width as defined by sender.
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Video height as defined by sender.
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Duration of the video in seconds as defined by sender.
	 */
	@Override
	public int getDuration() {
		return duration;
	}

	/**
	 * Optional. Video thumbnail.
	 */
	@Override
	public Optional<TelegramPhoto> getThumbnail() {
		return Optional.ofNullable(thumbnail);
	}

	/**
	 * Optional. Mime type of a file as defined by sender.
	 */
	@Override
	public Optional<String> getMimeType() {
		return Optional.ofNullable(mimeType);
	}

	@Override
	public Optional<Integer> getFileSize() {
		return Optional.ofNullable(fileSize);
	}

}
