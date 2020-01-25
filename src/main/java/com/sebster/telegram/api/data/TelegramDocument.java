package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * This object represents a general file (as opposed to photos, voice messages
 * and audio files).
 */
public final class TelegramDocument implements TelegramFile, WithThumbnail, WithMimeType, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String fileId;
	private final Optional<TelegramPhoto> thumbnail;
	private final Optional<String> fileName;
	private final Optional<String> mimeType;
	private final Optional<Integer> fileSize;

	public TelegramDocument(String fileId, Optional<TelegramPhoto> thumbnail, Optional<String> fileName,
			Optional<String> mimeType, Optional<Integer> fileSize) {
		this.fileId = requireNonNull(fileId, "fileId");
		this.thumbnail = requireNonNull(thumbnail, "thumbnail");
		this.fileName = requireNonNull(fileName, "fileName");
		this.mimeType = requireNonNull(mimeType, "mimeType");
		this.fileSize = requireNonNull(fileSize, "fileSize");
	}

	@Override
	public String getFileId() {
		return fileId;
	}

	/**
	 * Optional. Document thumbnail as defined by sender.
	 */
	@Override
	public Optional<TelegramPhoto> getThumbnail() {
		return thumbnail;
	}

	/**
	 * Optional. Original filename as defined by sender.
	 */
	public Optional<String> getFileName() {
		return fileName;
	}

	/**
	 * Optional. MIME type of the file as defined by sender.
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
