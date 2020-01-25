package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * This object represents a phone contact.
 */
public final class TelegramContact implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String phoneNumber;
	private final String firstName;
	private final Optional<String> lastName;
	private final Optional<String> userId;

	public TelegramContact(String phoneNumber, String firstName, Optional<String> lastName, Optional<String> userId) {
		this.phoneNumber = requireNonNull(phoneNumber, "phoneNumber");
		this.firstName = requireNonNull(firstName, "firstName");
		this.lastName = requireNonNull(lastName, "lastName");
		this.userId = requireNonNull(userId, "userId");
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
	public Optional<String> getUsername() {
		return userId;
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
