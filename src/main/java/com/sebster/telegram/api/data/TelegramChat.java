package com.sebster.telegram.api.data;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * This object represents a chat.
 */
public final class TelegramChat implements Serializable {

	private static final long serialVersionUID = 1L;

	private final long id;
	private final TelegramChatType type;
	private final Optional<String> title;
	private final Optional<String> username;
	private final Optional<String> firstName;
	private final Optional<String> lastName;

	public TelegramChat(long id, TelegramChatType type, Optional<String> title, Optional<String> username,
			Optional<String> firstName, Optional<String> lastName) {
		this.id = id;
		this.type = requireNonNull(type, "type");
		this.title = requireNonNull(title, "title");
		this.username = requireNonNull(username, "username");
		this.firstName = requireNonNull(firstName, "firstName");
		this.lastName = requireNonNull(lastName, "lastName");
	}

	/**
	 * Unique identifier for this chat, not exceeding 1e13 by absolute value.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Type of chat, can be either “private”, “group”, “supergroup” or
	 * “channel”.
	 */
	public TelegramChatType getType() {
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
