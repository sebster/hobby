package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramVoice;

/**
 * This object represents a voice note.
 */
public final class TelegramVoiceDto {

	private final String fileId;
	private final int duration;
	private final Optional<String> mimeType;
	private final Optional<Integer> fileSize;

	@JsonCreator
	public TelegramVoiceDto(@JsonProperty("file_id") String fileId, @JsonProperty("duration") int duration,
			@JsonProperty("mime_type") Optional<String> mimeType,
			@JsonProperty("file_size") Optional<Integer> fileSize) {
		this.fileId = fileId;
		this.duration = duration;
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
	 * Duration of the audio in seconds as defined by sender
	 */
	public int getDuration() {
		return duration;
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

	public TelegramVoice toTelegramVoice() {
		return new TelegramVoice(fileId, duration, mimeType, fileSize);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
