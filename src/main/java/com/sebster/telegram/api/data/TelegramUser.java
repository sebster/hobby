package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * This object represents a Telegram user or bot.
 */
public final class TelegramUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final int id;
	private final String firstName;
	private final Optional<String> lastName;
	private final Optional<String> username;

	public TelegramUser(int id, String firstName, Optional<String> lastName, Optional<String> username) {
		this.id = id;
		this.firstName = requireNonNull(firstName, "firstName");
		this.lastName = requireNonNull(lastName, "lastName");
		this.username = requireNonNull(username, "username");
	}

	/**
	 * Unique identifier for this user or bot.
	 */
	public int getId() {
		return id;
	}

	/**
	 * User‘s or bot’s first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Optional. User‘s or bot’s last name.
	 */
	public Optional<String> getLastName() {
		return lastName;
	}

	/**
	 * Optional. User‘s or bot’s username.
	 */
	public Optional<String> getUsername() {
		return username;
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
