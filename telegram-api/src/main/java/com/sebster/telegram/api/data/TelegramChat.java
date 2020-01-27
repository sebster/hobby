package com.sebster.telegram.api.data;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represents a chat.
 */
@Value
@EqualsAndHashCode(of = "id")
public class TelegramChat {

	long id;
	@NonNull TelegramChatType type;
	String title;
	String username;
	String firstName;
	String lastName;

	/**
	 * Unique identifier for this chat.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Type of chat, can be either “private”, “group”, “supergroup” or “channel”.
	 */
	public TelegramChatType getType() {
		return type;
	}

	/**
	 * Optional. Title, for supergroups, channels and group chats.
	 */
	public Optional<String> getTitle() {
		return Optional.ofNullable(title);
	}

	/**
	 * Optional. Username, for private chats, supergroups and channels if available.
	 */
	public Optional<String> getUsername() {
		return Optional.ofNullable(username);
	}

	/**
	 * Optional. First name of the other party in a private chat.
	 */
	public Optional<String> getFirstName() {
		return Optional.ofNullable(firstName);
	}

	/**
	 * Optional. Last name of the other party in a private chat.
	 */
	public Optional<String> getLastName() {
		return Optional.ofNullable(lastName);
	}

	// TODO:
	//	photo 	ChatPhoto 	Optional. Chat photo. Returned only in getChat.
	//	description 	String 	Optional. Description, for groups, supergroups and channel chats. Returned only in getChat.
	//	invite_link 	String 	Optional. Chat invite link, for groups, supergroups and channel chats. Each administrator in a chat generates their own invite links, so the bot must first generate the link using exportChatInviteLink. Returned only in getChat.
	//	pinned_message 	Message 	Optional. Pinned message, for groups, supergroups and channels. Returned only in getChat.
	//	permissions 	ChatPermissions 	Optional. Default chat member permissions, for groups and supergroups. Returned only in getChat.
	//	slow_mode_delay 	Integer 	Optional. For supergroups, the minimum allowed delay between consecutive messages sent by each unprivileged user. Returned only in getChat.
	//	sticker_set_name 	String 	Optional. For supergroups, name of group sticker set. Returned only in getChat.
	//	can_set_sticker_set 	Boolean 	Optional. True, if the bot can change the group sticker set. Returned only in getChat.

}
