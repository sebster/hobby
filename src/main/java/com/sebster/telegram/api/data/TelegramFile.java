package com.sebster.telegram.api.data;

import java.util.Optional;

/**
 * This interface represents a downloadable file.
 */
public interface TelegramFile {

	/**
	 * Unique identifier for this file
	 */
	String getFileId();

	/**
	 * Optional. File size
	 */
	Optional<Integer> getFileSize();

}
