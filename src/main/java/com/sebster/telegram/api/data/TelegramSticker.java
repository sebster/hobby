package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * This object represents a sticker.
 */
public final class TelegramSticker implements TelegramFile, WithDimension, WithThumbnail, Serializable {

	private static final long serialVersionUID = 1L;

	private final String fileId;
	private final int width;
	private final int height;
	private final Optional<TelegramPhoto> thumbnail;
	private final Optional<Integer> fileSize;

	public TelegramSticker(String fileId, int width, int height, Optional<TelegramPhoto> thumbnail,
			Optional<Integer> fileSize) {
		this.fileId = requireNonNull(fileId, "fileId");
		this.width = width;
		this.height = height;
		this.thumbnail = requireNonNull(thumbnail, "thumbnail");
		this.fileSize = requireNonNull(fileSize, "fileSize");
	}

	@Override
	public String getFileId() {
		return fileId;
	}

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
	 * Optional. Sticker thumbnail in .webp or .jpg format
	 */
	@Override
	public Optional<TelegramPhoto> getThumbnail() {
		return thumbnail;
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
