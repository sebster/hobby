package com.sebster.telegram.botapi;

import java.util.Optional;

import com.sebster.telegram.botapi.messages.TelegramMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * This object represents an incoming update.
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "updateId")
@ToString(doNotUseGetters = true)
@Builder(toBuilder = true)
public final class TelegramUpdate {

	int updateId;
	TelegramMessage message;

	/**
	 * The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially. This ID
	 * becomes especially handy if you’re using Webhooks, since it allows you to ignore repeated updates or to restore the correct
	 * update sequence, should they get out of order.
	 */
	public int getUpdateId() {
		return updateId;
	}

	/**
	 * Optional. New incoming message of any kind — text, photo, sticker, etc.
	 */
	public Optional<TelegramMessage> getMessage() {
		return Optional.ofNullable(message);
	}

}
