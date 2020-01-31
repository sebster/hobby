package com.sebster.telegram.botapi.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represents a Telegram user or bot.
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
public class TelegramUser {

	int id;
	boolean bot;
	@NonNull String firstName;
	String lastName;
	String username;
	String languageCode;

	/**
	 * Unique identifier for this user or bot.
	 */
	public int getId() {
		return id;
	}

	/**
	 * True, if this user is a bot.
	 */
	boolean isBot() {
		return bot;
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
		return Optional.ofNullable(lastName);
	}

	/**
	 * Optional. User‘s or bot’s username.
	 */
	public Optional<String> getUsername() {
		return Optional.ofNullable(username);
	}

	/**
	 * Optional. IETF language tag of the user's language.
	 */
	public Optional<String> getLanguageCode() {
		return Optional.ofNullable(languageCode);
	}

	// TODO:
	//	can_join_groups 	Boolean 	Optional. True, if the bot can be invited to groups. Returned only in getMe.
	//	can_read_all_group_messages 	Boolean 	Optional. True, if privacy mode is disabled for the bot. Returned only in getMe.
	//	supports_inline_queries 	Boolean 	Optional. True, if the bot supports inline queries. Returned only in getMe.

}
