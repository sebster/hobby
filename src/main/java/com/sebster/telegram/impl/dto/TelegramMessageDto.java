package com.sebster.telegram.impl.dto;

import static com.sebster.telegram.api.data.TelegramChatType.CHANNEL;
import static com.sebster.telegram.api.data.TelegramChatType.GROUP;
import static com.sebster.telegram.api.data.TelegramChatType.SUPERGROUP;
import static com.sebster.telegram.impl.util.TelegramUtils.unixTimeToDate;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.messages.TelegramAudioMessage;
import com.sebster.telegram.api.data.messages.TelegramChatCreatedMessage;
import com.sebster.telegram.api.data.messages.TelegramChatMigratedFromGroupMessage;
import com.sebster.telegram.api.data.messages.TelegramChatMigratedToSupergroupMessage;
import com.sebster.telegram.api.data.messages.TelegramChatPhotoChangedMessage;
import com.sebster.telegram.api.data.messages.TelegramChatPhotoDeletedMessage;
import com.sebster.telegram.api.data.messages.TelegramChatTitleChangedMessage;
import com.sebster.telegram.api.data.messages.TelegramContactMessage;
import com.sebster.telegram.api.data.messages.TelegramDocumentMessage;
import com.sebster.telegram.api.data.messages.TelegramLocationMessage;
import com.sebster.telegram.api.data.messages.TelegramMessage;
import com.sebster.telegram.api.data.messages.TelegramPhotoMessage;
import com.sebster.telegram.api.data.messages.TelegramStickerMessage;
import com.sebster.telegram.api.data.messages.TelegramTextMessage;
import com.sebster.telegram.api.data.messages.TelegramUnknownMessage;
import com.sebster.telegram.api.data.messages.TelegramUserJoinedChatMessage;
import com.sebster.telegram.api.data.messages.TelegramUserLeftChatMessage;
import com.sebster.telegram.api.data.messages.TelegramVideoMessage;
import com.sebster.telegram.api.data.messages.TelegramVoiceMessage;
import com.sebster.telegram.impl.util.TelegramUtils;

/**
 * This object represents a message.
 */
public final class TelegramMessageDto {

	private final int messageId;
	private final Optional<TelegramUserDto> from;
	private final int date;
	private final TelegramChatDto chat;
	private final Optional<TelegramUserDto> forwardFrom;
	private final Optional<Integer> forwardDate;
	private final Optional<TelegramMessageDto> replyToMessage;
	private final Optional<String> text;
	private final Optional<TelegramAudioDto> audio;
	private final Optional<TelegramDocumentDto> document;
	private final Optional<List<TelegramPhotoSizeDto>> photo;
	private final Optional<TelegramStickerDto> sticker;
	private final Optional<TelegramVideoDto> video;
	private final Optional<TelegramVoiceDto> voice;
	private final Optional<String> caption;
	private final Optional<TelegramContactDto> contact;
	private final Optional<TelegramLocationDto> location;
	private final Optional<TelegramUserDto> newChatParticipant;
	private final Optional<TelegramUserDto> leftChatParticipant;
	private final Optional<String> newChatTitle;
	private final Optional<List<TelegramPhotoSizeDto>> newChatPhoto;
	private final Optional<Boolean> deleteChatPhoto;
	private final Optional<Boolean> groupChatCreated;
	private final Optional<Boolean> superGroupChatCreated;
	private final Optional<Boolean> channelChatCreated;
	private final Optional<Long> migrateToChatId;
	private final Optional<Long> migrateFromChatId;

	public TelegramMessageDto(@JsonProperty("message_id") int messageId,
			@JsonProperty("from") Optional<TelegramUserDto> from, @JsonProperty("date") int date,
			@JsonProperty("chat") TelegramChatDto chat,
			@JsonProperty("forward_from") Optional<TelegramUserDto> forwardFrom,
			@JsonProperty("forward_date") Optional<Integer> forwardDate,
			@JsonProperty("reply_to_message") Optional<TelegramMessageDto> replyToMessage,
			@JsonProperty("text") Optional<String> text, @JsonProperty("audio") Optional<TelegramAudioDto> audio,
			@JsonProperty("document") Optional<TelegramDocumentDto> document,
			@JsonProperty("photo") Optional<List<TelegramPhotoSizeDto>> photo,
			@JsonProperty("sticker") Optional<TelegramStickerDto> sticker,
			@JsonProperty("video") Optional<TelegramVideoDto> video,
			@JsonProperty("voice") Optional<TelegramVoiceDto> voice, @JsonProperty("caption") Optional<String> caption,
			@JsonProperty("contact") Optional<TelegramContactDto> contact,
			@JsonProperty("location") Optional<TelegramLocationDto> location,
			@JsonProperty("new_chat_participant") Optional<TelegramUserDto> newChatParticipant,
			@JsonProperty("left_chat_participant") Optional<TelegramUserDto> leftChatParticipant,
			@JsonProperty("new_chat_title") Optional<String> newChatTitle,
			@JsonProperty("new_chat_photo") Optional<List<TelegramPhotoSizeDto>> newChatPhoto,
			@JsonProperty("delete_chat_photo") Optional<Boolean> deleteChatPhoto,
			@JsonProperty("group_chat_created") Optional<Boolean> groupChatCreated,
			@JsonProperty("supergroup_chat_created") Optional<Boolean> supergroupChatCreated,
			@JsonProperty("channel_chat_created") Optional<Boolean> channelChatCreated,
			@JsonProperty("migrate_to_chat_id") Optional<Long> migrateToChatId,
			@JsonProperty("migrate_from_chat_id") Optional<Long> migrateFromChatId) {

		this.messageId = messageId;
		this.from = from;
		this.date = date;
		this.chat = chat;
		this.forwardFrom = forwardFrom;
		this.forwardDate = forwardDate;
		this.replyToMessage = replyToMessage;
		this.text = text;
		this.audio = audio;
		this.document = document;
		this.photo = photo;
		this.sticker = sticker;
		this.video = video;
		this.voice = voice;
		this.caption = caption;
		this.contact = contact;
		this.location = location;
		this.newChatParticipant = newChatParticipant;
		this.leftChatParticipant = leftChatParticipant;
		this.newChatTitle = newChatTitle;
		this.newChatPhoto = newChatPhoto;
		this.deleteChatPhoto = deleteChatPhoto;
		this.groupChatCreated = groupChatCreated;
		this.superGroupChatCreated = supergroupChatCreated;
		this.channelChatCreated = channelChatCreated;
		this.migrateToChatId = migrateToChatId;
		this.migrateFromChatId = migrateFromChatId;
	}

	/**
	 * Unique message identifier.
	 */
	public int getMessageId() {
		return messageId;
	}

	/**
	 * Optional. Sender, can be empty for messages sent to channels.
	 */
	public Optional<TelegramUserDto> getFrom() {
		return from;
	}

	/**
	 * Date the message was sent in Unix time.
	 */
	public int getDate() {
		return date;
	}

	/**
	 * Conversation the message belongs to.
	 */
	public TelegramChatDto getChat() {
		return chat;
	}

	/**
	 * Optional. For forwarded messages, sender of the original message.
	 */
	public Optional<TelegramUserDto> getForwardFrom() {
		return forwardFrom;
	}

	/**
	 * Optional. For forwarded messages, date the original message was sent in
	 * Unix time.
	 */
	public Optional<Integer> getForwardDate() {
		return forwardDate;
	}

	/**
	 * Optional. For replies, the original message. Note that the Message object
	 * in this field will not contain further reply_to_message fields even if it
	 * itself is a reply.
	 */
	public Optional<TelegramMessageDto> getReplyToMessage() {
		return replyToMessage;
	}

	/**
	 * Optional. For text messages, the actual UTF-8 text of the message.
	 */
	public Optional<String> getText() {
		return text;
	}

	/**
	 * Optional. Message is an audio file, information about the file.
	 */
	public Optional<TelegramAudioDto> getAudio() {
		return audio;
	}

	/**
	 * Optional. Message is a general file, information about the file.
	 */
	public Optional<TelegramDocumentDto> getDocument() {
		return document;
	}

	/**
	 * Optional. Message is a photo, available sizes of the photo.
	 */
	public Optional<List<TelegramPhotoSizeDto>> getPhoto() {
		return photo;
	}

	/**
	 * Optional. Message is a sticker, information about the sticker
	 */
	public Optional<TelegramStickerDto> getSticker() {
		return sticker;
	}

	/**
	 * Optional. Message is a video, information about the video
	 */
	public Optional<TelegramVideoDto> getVideo() {
		return video;
	}

	/**
	 * Optional. Message is a voice message, information about the file.
	 */
	public Optional<TelegramVoiceDto> getVoice() {
		return voice;
	}

	/**
	 * Optional. Caption for the photo or video.
	 */
	public Optional<String> getCaption() {
		return caption;
	}

	/**
	 * Optional. Message is a shared contact, information about the contact.
	 */
	public Optional<TelegramContactDto> getContact() {
		return contact;
	}

	/**
	 * Optional. Message is a shared location, information about the location.
	 */
	public Optional<TelegramLocationDto> getLocation() {
		return location;
	}

	/**
	 * Optional. A new member was added to the group, information about them
	 * (this member may be bot itself).
	 */
	public Optional<TelegramUserDto> getNewChatParticipant() {
		return newChatParticipant;
	}

	/**
	 * Optional. A member was removed from the group, information about them
	 * (this member may be bot itself).
	 */
	public Optional<TelegramUserDto> getLeftChatParticipant() {
		return leftChatParticipant;
	}

	/**
	 * Optional. A chat title was changed to this value.
	 */
	public Optional<String> getNewChatTitle() {
		return newChatTitle;
	}

	/**
	 * Optional. A chat photo was change to this value.
	 */
	public Optional<List<TelegramPhotoSizeDto>> getNewChatPhoto() {
		return newChatPhoto;
	}

	/**
	 * Optional. Informs that the chat photo was deleted.
	 */
	public Optional<Boolean> isDeleteChatPhoto() {
		return deleteChatPhoto;
	}

	/**
	 * Optional. Informs that the group has been created.
	 */
	public Optional<Boolean> isGroupChatCreated() {
		return groupChatCreated;
	}

	/**
	 * Optional. Service message: the supergroup has been created.
	 */
	public Optional<Boolean> isSuperGroupChatCreated() {
		return superGroupChatCreated;
	}

	/**
	 * Optional. Service message: the channel has been created.
	 */
	public Optional<Boolean> isChannelChatCreated() {
		return channelChatCreated;
	}

	/**
	 * Optional. The group has been migrated to a supergroup with the specified
	 * identifier, not exceeding 1e13 by absolute value.
	 */
	public Optional<Long> getMigrateToChatId() {
		return migrateToChatId;
	}

	/**
	 * Optional. The supergroup has been migrated from a group with the
	 * specified identifier, not exceeding 1e13 by absolute value.
	 */
	public Optional<Long> getMigrateFromChatId() {
		return migrateFromChatId;
	}

	public TelegramMessage toTelegramMessage() {
		Optional<TelegramUser> from = this.from.map(TelegramUserDto::toTelegramUser);
		Date date = unixTimeToDate(this.date);
		TelegramChat chat = this.chat.toTelegramChat();
		Optional<TelegramUser> forwardFrom = this.forwardFrom.map(TelegramUserDto::toTelegramUser);
		Optional<Date> forwardDate = this.forwardDate.map(TelegramUtils::unixTimeToDate);
		Optional<TelegramMessage> replyToMessage = this.replyToMessage.map(TelegramMessageDto::toTelegramMessage);

		if (text.isPresent()) {
			return new TelegramTextMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					text.get());
		}
		if (audio.isPresent()) {
			return new TelegramAudioMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					audio.get().toTelegramAudio());
		}
		if (document.isPresent()) {
			return new TelegramDocumentMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					document.get().toTelegramDocument());
		}
		if (photo.isPresent()) {
			return new TelegramPhotoMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					TelegramPhotoSizeDto.toTelegramPhotoList(photo.get()), caption);
		}
		if (sticker.isPresent()) {
			return new TelegramStickerMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					sticker.get().toTelegramSticker());
		}
		if (video.isPresent()) {
			return new TelegramVideoMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					video.get().toTelegramVideo(), caption);
		}
		if (voice.isPresent()) {
			return new TelegramVoiceMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					voice.get().toTelegramVoice());
		}
		if (contact.isPresent()) {
			return new TelegramContactMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					contact.get().toTelegramContact());
		}
		if (location.isPresent()) {
			return new TelegramLocationMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					location.get().toTelegramLocation());
		}
		if (newChatParticipant.isPresent()) {
			return new TelegramUserJoinedChatMessage(messageId, from, date, chat, forwardFrom, forwardDate,
					replyToMessage, newChatParticipant.get().toTelegramUser());
		}
		if (leftChatParticipant.isPresent()) {
			return new TelegramUserLeftChatMessage(messageId, from, date, chat, forwardFrom, forwardDate,
					replyToMessage, leftChatParticipant.get().toTelegramUser());
		}
		if (newChatTitle.isPresent()) {
			return new TelegramChatTitleChangedMessage(messageId, from, date, chat, forwardFrom, forwardDate,
					replyToMessage, newChatTitle.get());
		}
		if (newChatPhoto.isPresent()) {
			return new TelegramChatPhotoChangedMessage(messageId, from, date, chat, forwardFrom, forwardDate,
					replyToMessage, TelegramPhotoSizeDto.toTelegramPhotoList(newChatPhoto.get()));
		}
		if (deleteChatPhoto.isPresent()) {
			return new TelegramChatPhotoDeletedMessage(messageId, from, date, chat, forwardFrom, forwardDate,
					replyToMessage);
		}
		if (groupChatCreated.isPresent()) {
			return new TelegramChatCreatedMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					GROUP);
		}
		if (superGroupChatCreated.isPresent()) {
			return new TelegramChatCreatedMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					SUPERGROUP);
		}
		if (channelChatCreated.isPresent()) {
			return new TelegramChatCreatedMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					CHANNEL);
		}
		if (migrateToChatId.isPresent()) {
			return new TelegramChatMigratedToSupergroupMessage(messageId, from, date, chat, forwardFrom, forwardDate,
					replyToMessage, migrateToChatId.get());
		}
		if (migrateFromChatId.isPresent()) {
			return new TelegramChatMigratedFromGroupMessage(messageId, from, date, chat, forwardFrom, forwardDate,
					replyToMessage, migrateFromChatId.get());
		}
		return new TelegramUnknownMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
