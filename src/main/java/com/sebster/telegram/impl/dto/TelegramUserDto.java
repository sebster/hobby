package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramUser;

/**
 * This object represents a Telegram user or bot.
 */
public final class TelegramUserDto {

	private final int id;
	private final String firstName;
	private final Optional<String> lastName;
	private final Optional<String> username;

	@JsonCreator
	public TelegramUserDto(@JsonProperty("id") int id, @JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") Optional<String> lastName, @JsonProperty("username") Optional<String> username) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
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

	public TelegramUser toTelegramUser() {
		return new TelegramUser(id, firstName, lastName, username);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
