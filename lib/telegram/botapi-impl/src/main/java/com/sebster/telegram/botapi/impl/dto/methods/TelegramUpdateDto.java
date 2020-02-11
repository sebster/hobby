package com.sebster.telegram.botapi.impl.dto.methods;

import com.sebster.telegram.botapi.TelegramUpdate;
import com.sebster.telegram.botapi.impl.dto.data.TelegramMessageDto;
import lombok.Data;

@Data
public class TelegramUpdateDto {

	/**
	 * The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially. This ID
	 * becomes especially handy if you’re using Webhooks, since it allows you to ignore repeated updates or to restore the correct
	 * update sequence, should they get out of order.
	 */
	int updateId;

	/**
	 * Optional. New incoming message of any kind — text, photo, sticker, etc.
	 */
	TelegramMessageDto message;

	public TelegramUpdate toTelegramUpdate() {
		return new TelegramUpdate(updateId, message != null ? message.toTelegramMessage() : null);
	}

}