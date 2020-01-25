package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramAudio;

/**
 * This object represents an audio file to be treated as music by the Telegram
 * clients.
 */
public final class TelegramAudioDto {

	private final String fileId;
	private final int duration;
	private final Optional<String> performer;
	private final Optional<String> title;
	private final Optional<String> mimeType;
	private final Optional<Integer> fileSize;

	@JsonCreator
	public TelegramAudioDto(@JsonProperty("file_id") String fileId, @JsonProperty("duration") int duration,
			@JsonProperty("performer") Optional<String> performer, @JsonProperty("title") Optional<String> title,
			@JsonProperty("mime_type") Optional<String> mimeType,
			@JsonProperty("file_size") Optional<Integer> fileSize) {
		this.fileId = fileId;
		this.duration = duration;
		this.performer = performer;
		this.title = title;
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
	 * Duration of the audio in seconds as defined by sender.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Optional. Performer of the audio as defined by sender or by audio tags.
	 */
	public Optional<String> getPerformer() {
		return performer;
	}

	/**
	 * Optional. Title of the audio as defined by sender or by audio tags.
	 */
	public Optional<String> getTitle() {
		return title;
	}

	/**
	 * Optional. MIME type of the file as defined by sender.
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

	public TelegramAudio toTelegramAudio() {
		return new TelegramAudio(fileId, duration, performer, title, mimeType, fileSize);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
