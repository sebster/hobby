package com.sebster.telegram.api.data;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represents a general file (as opposed to photos, voice messages and audio files).
 */
@Value
@EqualsAndHashCode(of = "fileUniqueId")
public class TelegramDocument implements TelegramFile, WithThumbnail, WithMimeType {

	@NonNull String fileId;
	@NonNull String fileUniqueId;
	TelegramPhoto thumbnail;
	String fileName;
	String mimeType;
	Integer fileSize;

	/**
	 * Optional. Document thumbnail as defined by sender.
	 */
	@Override
	public Optional<TelegramPhoto> getThumbnail() {
		return Optional.ofNullable(thumbnail);
	}

	/**
	 * Optional. Original filename as defined by sender.
	 */
	public Optional<String> getFileName() {
		return Optional.ofNullable(fileName);
	}

	/**
	 * Optional. MIME type of the file as defined by sender.
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
