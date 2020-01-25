package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramContact;

/**
 * This object represents a phone contact.
 */
public final class TelegramContactDto {

	private final String phoneNumber;
	private final String firstName;
	private final Optional<String> lastName;
	private final Optional<String> userId;

	@JsonCreator
	public TelegramContactDto(@JsonProperty("phone_number") String phoneNumber,
			@JsonProperty("first_name") String firstName, @JsonProperty("last_name") Optional<String> lastName,
			@JsonProperty("user_id") Optional<String> userId) {
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
	}

	/**
	 * Contact's phone number.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Contact's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Optional. Contact's last name.
	 */
	public Optional<String> getLastName() {
		return lastName;
	}

	/**
	 * Optional. Contact's user identifier in Telegram.
	 */
	public Optional<String> getUserId() {
		return userId;
	}

	public TelegramContact toTelegramContact() {
		return new TelegramContact(phoneNumber, firstName, lastName, userId);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
