package com.sebster.telegram.api.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represents a phone contact.
 */
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class TelegramContact {

	@NonNull String phoneNumber;
	@NonNull String firstName;
	String lastName;
	Integer userId;
	String vCard;

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
		return Optional.ofNullable(lastName);
	}

	/**
	 * Optional. Contact's user identifier in Telegram.
	 */
	public Optional<Integer> getUsername() {
		return Optional.ofNullable(userId);
	}

	/**
	 * Optional. Additional data about the contact in the form of a vCard.
	 */
	public Optional<String> getVCard() {
		return Optional.ofNullable(vCard);
	}

}
