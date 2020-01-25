package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramDocument;

/**
 * This object represents a general file (as opposed to photos, voice messages
 * and audio files).
 */
public final class TelegramDocumentDto {

	private final String fileId;
	private final Optional<TelegramPhotoSizeDto> thumb;
	private final Optional<String> fileName;
	private final Optional<String> mimeType;
	private final Optional<Integer> fileSize;

	@JsonCreator
	public TelegramDocumentDto(@JsonProperty("file_id") String fileId,
			@JsonProperty("thumb") Optional<TelegramPhotoSizeDto> thumb,
			@JsonProperty("file_name") Optional<String> fileName, @JsonProperty("mime_type") Optional<String> mimeType,
			@JsonProperty("file_size") Optional<Integer> fileSize) {
		this.fileId = fileId;
		this.thumb = thumb;
		this.fileName = fileName;
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
	 * Optional. Document thumbnail as defined by sender
	 */
	public Optional<TelegramPhotoSizeDto> getThumb() {
		return thumb;
	}

	/**
	 * Optional. Original filename as defined by sender
	 */
	public Optional<String> getFileName() {
		return fileName;
	}

	/**
	 * Optional. MIME type of the file as defined by sender
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

	public TelegramDocument toTelegramDocument() {
		return new TelegramDocument(fileId, thumb.map(TelegramPhotoSizeDto::toTelegramPhoto), fileName, mimeType,
				fileSize);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
