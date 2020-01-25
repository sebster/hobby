package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * This object represents an audio file to be treated as music by the Telegram
 * clients.
 */
public final class TelegramAudio implements TelegramFile, WithDuration, WithMimeType, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String fileId;
	private final int duration;
	private final Optional<String> performer;
	private final Optional<String> title;
	private final Optional<String> mimeType;
	private final Optional<Integer> fileSize;

	public TelegramAudio(String fileId, int duration, Optional<String> performer, Optional<String> title,
			Optional<String> mimeType, Optional<Integer> fileSize) {
		this.fileId = requireNonNull(fileId, "fileId");
		this.duration = duration;
		this.performer = requireNonNull(performer, "performer");
		this.title = requireNonNull(title, "title");
		this.mimeType = requireNonNull(mimeType, "mimeType");
		this.fileSize = requireNonNull(fileSize, "fileSize");
	}

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
	 * Duration of the audio in seconds as defined by sender.
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
	 * Optional. MIME type of the file as defined by sender
	 */
	@Override
	public Optional<String> getMimeType() {
		return mimeType;
	}

	@Override
	public final Optional<Integer> getFileSize() {
		return fileSize;
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
