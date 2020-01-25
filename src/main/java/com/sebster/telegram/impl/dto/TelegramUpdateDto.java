package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramUpdate;

/**
 * This object represents an incoming update.
 */
public final class TelegramUpdateDto {

	private final int updateId;
	private final Optional<TelegramMessageDto> message;

	@JsonCreator
	public TelegramUpdateDto(@JsonProperty("update_id") int updateId,
			@JsonProperty("message") Optional<TelegramMessageDto> message) {
		this.updateId = updateId;
		this.message = message;
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
	public Optional<TelegramMessageDto> getMessage() {
		return message;
	}

	public TelegramUpdate toTelegramUpdate() {
		return new TelegramUpdate(updateId, message.map(TelegramMessageDto::toTelegramMessage));
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}
}
