package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramFileLink;

/**
 * This object represents a file ready to be downloaded. The file can be
 * downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>.
 * It is guaranteed that the link will be valid for at least 1 hour. When the
 * link expires, a new one can be requested by calling getFile.
 *
 * Maximum file size to download is 20 MB.
 */
public final class TelegramFileDto {

	private final String fileId;
	private final Optional<Integer> fileSize;
	private final Optional<String> filePath;

	@JsonCreator
	public TelegramFileDto(@JsonProperty("file_id") String fileId,
			@JsonProperty("file_size") Optional<Integer> fileSize,
			@JsonProperty("file_path") Optional<String> filePath) {
		this.fileId = fileId;
		this.fileSize = fileSize;
		this.filePath = filePath;
	}

	/**
	 * Unique identifier for this file.
	 */
	public final String getFileId() {
		return fileId;
	}

	/**
	 * Optional. File path. Use https://api.telegram.org/file/bot<token>/
	 * <file_path> to get the file.
	 */
	public Optional<String> getFilePath() {
		return filePath;
	}

	/**
	 * Optional. File size.
	 */
	public final Optional<Integer> getFileSize() {
		return fileSize;
	}

	public TelegramFileLink toTelegramFileLink(URL baseUrl) throws MalformedURLException {
		Optional<URL> fileUrl = filePath.isPresent() ? Optional.of(new URL(baseUrl, filePath.get())) : Optional.empty();
		return new TelegramFileLink(fileId, fileSize, filePath, fileUrl);
	}
	
	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
