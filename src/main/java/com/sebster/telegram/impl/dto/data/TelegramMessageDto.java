package com.sebster.telegram.impl.dto.data;

import static com.sebster.telegram.api.data.TelegramChatType.CHANNEL;
import static com.sebster.telegram.api.data.TelegramChatType.GROUP;
import static com.sebster.telegram.api.data.TelegramChatType.SUPERGROUP;
import static com.sebster.telegram.impl.dto.data.TelegramPhotoSizeDto.toTelegramPhotoList;

import java.util.Date;
import java.util.List;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.messages.TelegramAudioMessage;
import com.sebster.telegram.api.messages.TelegramChatCreatedMessage;
import com.sebster.telegram.api.messages.TelegramChatMigratedFromGroupMessage;
import com.sebster.telegram.api.messages.TelegramChatMigratedToSupergroupMessage;
import com.sebster.telegram.api.messages.TelegramChatPhotoChangedMessage;
import com.sebster.telegram.api.messages.TelegramChatPhotoDeletedMessage;
import com.sebster.telegram.api.messages.TelegramChatTitleChangedMessage;
import com.sebster.telegram.api.messages.TelegramContactMessage;
import com.sebster.telegram.api.messages.TelegramDocumentMessage;
import com.sebster.telegram.api.messages.TelegramLocationMessage;
import com.sebster.telegram.api.messages.TelegramMessage;
import com.sebster.telegram.api.messages.TelegramPhotoMessage;
import com.sebster.telegram.api.messages.TelegramStickerMessage;
import com.sebster.telegram.api.messages.TelegramTextMessage;
import com.sebster.telegram.api.messages.TelegramUnknownMessage;
import com.sebster.telegram.api.messages.TelegramUserJoinedChatMessage;
import com.sebster.telegram.api.messages.TelegramUserLeftChatMessage;
import com.sebster.telegram.api.messages.TelegramVideoMessage;
import com.sebster.telegram.api.messages.TelegramVoiceMessage;
import lombok.Data;

@Data
public class TelegramMessageDto {

	int messageId;
	TelegramUserDto from;
	int date;
	TelegramChatDto chat;
	TelegramUserDto forwardFrom;
	Integer forwardDate;
	TelegramMessageDto replyToMessage;
	String text;
	TelegramAudioDto audio;
	TelegramDocumentDto document;
	List<TelegramPhotoSizeDto> photo;
	TelegramStickerDto sticker;
	TelegramVideoDto video;
	TelegramVoiceDto voice;
	String caption;
	TelegramContactDto contact;
	TelegramLocationDto location;
	TelegramUserDto newChatParticipant;
	TelegramUserDto leftChatParticipant;
	String newChatTitle;
	List<TelegramPhotoSizeDto> newChatPhoto;
	Boolean deleteChatPhoto;
	Boolean groupChatCreated;
	Boolean supergroupChatCreated;
	Boolean channelChatCreated;
	Long migrateToChatId;
	Long migrateFromChatId;

	public TelegramMessage toTelegramMessage() {
		TelegramUser from = this.from != null ? this.from.toTelegramUser() : null;
		Date date = unixTimeToDate(this.date);
		TelegramChat chat = this.chat.toTelegramChat();
		TelegramUser forwardFrom = this.forwardFrom != null ? this.forwardFrom.toTelegramUser() : null;
		Date forwardDate = this.forwardDate != null ? unixTimeToDate(this.forwardDate) : null;
		TelegramMessage replyToMessage = this.replyToMessage != null ? this.replyToMessage.toTelegramMessage() : null;

		if (text != null) {
			return new TelegramTextMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage, text);
		}
		if (audio != null) {
			return new TelegramAudioMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					audio.toTelegramAudio()
			);
		}
		if (document != null) {
			return new TelegramDocumentMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					document.toTelegramDocument()
			);
		}
		if (photo != null) {
			return new TelegramPhotoMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					toTelegramPhotoList(photo), caption
			);
		}
		if (sticker != null) {
			return new TelegramStickerMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					sticker.toTelegramSticker()
			);
		}
		if (video != null) {
			return new TelegramVideoMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					video.toTelegramVideo(), caption
			);
		}
		if (voice != null) {
			return new TelegramVoiceMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					voice.toTelegramVoice()
			);
		}
		if (contact != null) {
			return new TelegramContactMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					contact.toTelegramContact()
			);
		}
		if (location != null) {
			return new TelegramLocationMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					location.toTelegramLocation()
			);
		}
		if (newChatParticipant != null) {
			return new TelegramUserJoinedChatMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					newChatParticipant.toTelegramUser()
			);
		}
		if (leftChatParticipant != null) {
			return new TelegramUserLeftChatMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					leftChatParticipant.toTelegramUser()
			);
		}
		if (newChatTitle != null) {
			return new TelegramChatTitleChangedMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					newChatTitle
			);
		}
		if (newChatPhoto != null) {
			return new TelegramChatPhotoChangedMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					toTelegramPhotoList(newChatPhoto));
		}
		if (deleteChatPhoto != null) {
			return new TelegramChatPhotoDeletedMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
		}
		if (groupChatCreated != null) {
			return new TelegramChatCreatedMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					GROUP
			);
		}
		if (supergroupChatCreated != null) {
			return new TelegramChatCreatedMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					SUPERGROUP
			);
		}
		if (channelChatCreated != null) {
			return new TelegramChatCreatedMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					CHANNEL
			);
		}
		if (migrateToChatId != null) {
			return new TelegramChatMigratedToSupergroupMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					migrateToChatId
			);
		}
		if (migrateFromChatId != null) {
			return new TelegramChatMigratedFromGroupMessage(
					messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage,
					migrateFromChatId
			);
		}
		return new TelegramUnknownMessage(messageId, from, date, chat, forwardFrom, forwardDate, replyToMessage);
	}

	private Date unixTimeToDate(int unixTime) {
		return new Date(unixTime * 1000L);
	}

}
