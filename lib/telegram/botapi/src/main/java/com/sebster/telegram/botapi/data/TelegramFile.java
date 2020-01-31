package com.sebster.telegram.botapi.data;

import java.util.Optional;

import lombok.NonNull;

/**
 * This interface represents any object that is a file. To download the file, the getFile API method should be used to get a {@link
 * TelegramFileLink}.
 */
public interface TelegramFile {

	/**
	 * Identifier for this file, which can be used to download or reuse the file.
	 */
	String getFileId();

	/**
	 * Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download
	 * or reuse the file.
	 */
	String getFileUniqueId();

	/**
	 * Optional. File size, if known.
	 */
	Optional<Integer> getFileSize();

	interface TelegramFileBuilder {

		TelegramFileBuilder fileId(@NonNull String fileId);

		TelegramFileBuilder fileUniqueId(@NonNull String fileUniqueId);

		TelegramFileBuilder fileSize(Integer fileSize);

		TelegramFile build();

	}

}
