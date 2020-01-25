package com.sebster.telegram.impl.dto;

import static com.sebster.telegram.impl.util.TelegramUtils.toChatType;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramChat;

/**
 * This object represents a chat.
 */
public final class TelegramChatDto {

	private final long id;
	private final String type;
	private final Optional<String> title;
	private final Optional<String> username;
	private final Optional<String> firstName;
	private final Optional<String> lastName;

	@JsonCreator
	public TelegramChatDto(@JsonProperty("id") long id, @JsonProperty("type") String type,
			@JsonProperty("title") Optional<String> title, @JsonProperty("username") Optional<String> username,
			@JsonProperty("first_name") Optional<String> firstName,
			@JsonProperty("last_name") Optional<String> lastName) {
		this.id = id;
		this.type = type;
		this.title = title;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Unique identifier for this chat, not exceeding 1e13 by absolute value.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Type of chat, can be either “private”, or “group”, or "supergroup", or
	 * “channel”.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Optional. Title, for channels and group chats.
	 */
	public Optional<String> getTitle() {
		return title;
	}

	/**
	 * Optional. Username, for private chats and channels if available.
	 */
	public Optional<String> getUsername() {
		return username;
	}

	/**
	 * Optional. First name of the other party in a private chat.
	 */
	public Optional<String> getFirstName() {
		return firstName;
	}

	/**
	 * Optional. Last name of the other party in a private chat.
	 */
	public Optional<String> getLastName() {
		return lastName;
	}

	public TelegramChat toTelegramChat() {
		return new TelegramChat(id, toChatType(type), title, username, firstName, lastName);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}
}
