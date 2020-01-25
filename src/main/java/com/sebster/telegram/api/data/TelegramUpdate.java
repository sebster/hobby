package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

import com.sebster.telegram.api.data.messages.TelegramMessage;

/**
 * This object represents an incoming update.
 */
public final class TelegramUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int updateId;
	private final Optional<TelegramMessage> message;

	public TelegramUpdate(int updateId, Optional<TelegramMessage> message) {
		this.updateId = updateId;
		this.message = requireNonNull(message, "message");
	}

	/**
	 * The update‘s unique identifier. Update identifiers start from a certain
	 * positive number and increase sequentially. This ID becomes especially
	 * handy if you’re using Webhooks, since it allows you to ignore repeated
	 * updates or to restore the correct update sequence, should they get out of
	 * order.
	 */
	public int getUpdateId() {
		return updateId;
	}

	/**
	 * Optional. New incoming message of any kind — text, photo, sticker, etc.
	 */
	public Optional<TelegramMessage> getMessage() {
		return message;
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
