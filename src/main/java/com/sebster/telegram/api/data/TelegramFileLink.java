package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.net.URL;
import java.util.Optional;

/**
 * This object represents a link to a file ready to be downloaded. The file can
 * be downloaded via the link https://api.telegram.org/file/bot<token>/
 * <file_path>. It is guaranteed that the link will be valid for at least 1
 * hour. When the link expires, a new one can be requested by calling getFile.
 *
 * Maximum file size to download is 20 MB
 */
public final class TelegramFileLink implements TelegramFile, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String fileId;
	private final Optional<Integer> fileSize;
	private final Optional<String> filePath;
	private final Optional<URL> fileUrl;

	public TelegramFileLink(String fileId, Optional<Integer> fileSize, Optional<String> filePath,
			Optional<URL> fileUrl) {
		this.fileId = requireNonNull(fileId, "fileId");
		this.fileSize = requireNonNull(fileSize, "fileSize");
		this.filePath = requireNonNull(filePath, "filePath");
		this.fileUrl = requireNonNull(fileUrl, "fileUrl");
	}

	@Override
	public String getFileId() {
		return fileId;
	}

	@Override
	public Optional<Integer> getFileSize() {
		return fileSize;
	}

	/**
	 * Optional. File path. Use https://api.telegram.org/file/bot<token>/
	 * <file_path> to get the file.
	 */
	public Optional<String> getFilePath() {
		return filePath;
	}

	/**
	 * Optional. File url. This is equal to https://api.telegram.org/file/bot
	 * <token>/ <file_path>.
	 */

	public Optional<URL> getFileUrl() {
		return fileUrl;
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
