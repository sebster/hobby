package com.sebster.telegram.botapi.impl.dto.data;

import com.sebster.telegram.botapi.data.TelegramContact;
import lombok.Data;

@Data
public final class TelegramContactDto {

	String phoneNumber;
	String firstName;
	String lastName;
	Integer userId;
	String vcard;

	public TelegramContact toTelegramContact() {
		return new TelegramContact(phoneNumber, firstName, lastName, userId, vcard);
	}

}
