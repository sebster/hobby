package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 */
public final class TelegramPhoto implements TelegramFile, WithDimension, Serializable {

	private static final long serialVersionUID = 1L;

	private final String fileId;
	private final Optional<Integer> fileSize;
	private final int width;
	private final int height;

	public TelegramPhoto(String fileId, int width, int height, Optional<Integer> fileSize) {
		this.fileId = requireNonNull(fileId, "fileId");
		this.fileSize = requireNonNull(fileSize, "fileSize");
		this.width = width;
		this.height = height;
	}

	@Override
	public String getFileId() {
		return fileId;
	}

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
