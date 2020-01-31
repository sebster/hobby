package com.sebster.telegram.botapi.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represents an audio file to be treated as music by the Telegram clients.
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "fileUniqueId")
@Builder(toBuilder = true)
public class TelegramAudio implements TelegramFile, WithDuration, WithMimeType {

	@NonNull String fileId;
	@NonNull String fileUniqueId;
	int duration;
	String performer;
	String title;
	String mimeType;
	Integer fileSize;

	/**
	 * Identifier for this file, which can be used to download or reuse the file.
	 */
	@Override
	public final String getFileId() {
		return fileId;
	}

	/**
	 * Duration of the audio in seconds as defined by sender.
	 */
	@Override
	public int getDuration() {
		return duration;
	}

	/**
	 * Optional. Performer of the audio as defined by sender or by audio tags.
	 */
	public Optional<String> getPerformer() {
		return Optional.ofNullable(performer);
	}

	/**
	 * Optional. Title of the audio as defined by sender or by audio tags.
	 */
	public Optional<String> getTitle() {
		return Optional.ofNullable(title);
	}

	/**
	 * Optional. MIME type of the file as defined by sender.
	 */
	@Override
	public Optional<String> getMimeType() {
		return Optional.ofNullable(mimeType);
	}

	@Override
	public final Optional<Integer> getFileSize() {
		return Optional.ofNullable(fileSize);
	}

	public static class TelegramAudioBuilder implements TelegramFileBuilder, TelegramDurationBuilder, TelegramMimeTypeBuilder {
	}

}
