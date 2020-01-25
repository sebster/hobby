package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * This object represents a video file.
 */
public final class TelegramVideo
		implements TelegramFile, WithDimension, WithDuration, WithThumbnail, WithMimeType, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String fileId;
	private final int width;
	private final int height;
	private final int duration;
	private final Optional<TelegramPhoto> thumbnail;
	private final Optional<String> mimeType;
	private final Optional<Integer> fileSize;

	public TelegramVideo(String fileId, int width, int height, int duration, Optional<TelegramPhoto> thumbnail,
			Optional<String> mimeType, Optional<Integer> fileSize) {
		this.fileId = requireNonNull(fileId, "fileId");
		this.width = width;
		this.height = height;
		this.duration = duration;
		this.thumbnail = requireNonNull(thumbnail, "thumbnail");
		this.mimeType = requireNonNull(mimeType, "mimeType");
		this.fileSize = requireNonNull(fileSize, "fileSize");
	}

	@Override
	public String getFileId() {
		return fileId;
	}

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
		return thumbnail;
	}

	/**
	 * Optional. Mime type of a file as defined by sender.
	 */
	@Override
	public Optional<String> getMimeType() {
		return mimeType;
	}

	@Override
	public Optional<Integer> getFileSize() {
		return fileSize;
	}

	@Override
	public final boolean equals(Object obj) {
		return reflectionEquals(this, obj, false);
	}

	@Override
	public final int hashCode() {
		return reflectionHashCode(this, false);
	}

	@Override
	public final String toString() {
		return reflectionToString(this);
	}

}
