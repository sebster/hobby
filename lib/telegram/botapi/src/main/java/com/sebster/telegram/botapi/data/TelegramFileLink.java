package com.sebster.telegram.botapi.data;

import java.net.URL;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represents a link to a file ready to be downloaded. The file can be downloaded via the URL
 * https://api.telegram.org/file/bot&lt;token&gt;/ &lt;file_path&gt;. It is guaranteed that the link will be valid for at least 1
 * hour. When the link expires, a new one can be requested by calling the getFile API method..
 * <p>
 * Maximum file size to download is 20 MB.
 */
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class TelegramFileLink implements TelegramFile {

	@NonNull String fileId;
	@NonNull String fileUniqueId;
	Integer fileSize;
	String filePath;
	URL fileUrl;

	@Override
	public Optional<Integer> getFileSize() {
		return Optional.ofNullable(fileSize);
	}

	/**
	 * Optional. File path. Use https://api.telegram.org/file/bot&lt;token&gt;/&lt;file_path&gt; to get the file.
	 */
	public Optional<String> getFilePath() {
		return Optional.ofNullable(filePath);
	}

	/**
	 * Optional. File url. This is equal to https://api.telegram.org/file/bot&lt;token&gt;/&lt;file_path&gt;.
	 */
	public Optional<URL> getFileUrl() {
		return Optional.ofNullable(fileUrl);
	}

	public static class TelegramFileLinkBuilder implements TelegramFileBuilder {
	}

}
