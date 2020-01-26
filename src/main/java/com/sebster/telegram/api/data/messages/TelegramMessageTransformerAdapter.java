package com.sebster.telegram.api.data.messages;

public class TelegramMessageTransformerAdapter<RETURN_TYPE> implements TelegramMessageTransformer<RETURN_TYPE> {

	@Override
	public RETURN_TYPE transformTextMessage(TelegramTextMessage textMessage) {
		return transformMessage(textMessage);
	}

	@Override
	public RETURN_TYPE transformAudioMessage(TelegramAudioMessage audioMessage) {
		return transformMessage(audioMessage);
	}

	@Override
	public RETURN_TYPE transformContactMessage(TelegramContactMessage contactMessage) {
		return transformMessage(contactMessage);
	}

	@Override
	public RETURN_TYPE transformDocumentMessage(TelegramDocumentMessage documentMessage) {
		return transformMessage(documentMessage);
	}

	@Override
	public RETURN_TYPE transformPhotoMessage(TelegramPhotoMessage photoMessage) {
		return transformMessage(photoMessage);
	}

	@Override
	public RETURN_TYPE transformStickerMessage(TelegramStickerMessage stickerMessage) {
		return transformMessage(stickerMessage);
	}

	@Override
	public RETURN_TYPE transformVoiceMessage(TelegramVoiceMessage voiceMessage) {
		return transformMessage(voiceMessage);
	}

	@Override
	public RETURN_TYPE transformVideoMessage(TelegramVideoMessage videoMessage) {
		return transformMessage(videoMessage);
	}

	@Override
	public RETURN_TYPE transformLocationMessage(TelegramLocationMessage locationMessage) {
		return transformMessage(locationMessage);
	}

	@Override
	public RETURN_TYPE transformUserJoinedChatMessage(TelegramUserJoinedChatMessage userJoinedChatMessage) {
		return transformMessage(userJoinedChatMessage);
	}

	@Override
	public RETURN_TYPE transformUserLeftChatMessage(TelegramUserLeftChatMessage userLeftChatMessage) {
		return transformMessage(userLeftChatMessage);
	}

	@Override
	public RETURN_TYPE transformChatTitleChangedMessage(TelegramChatTitleChangedMessage chatTitleChangedMessage) {
		return transformMessage(chatTitleChangedMessage);
	}

	@Override
	public RETURN_TYPE transformChatPhotoChangedMessage(TelegramChatPhotoChangedMessage chatPhotoChangedMessage) {
		return transformMessage(chatPhotoChangedMessage);
	}

	@Override
	public RETURN_TYPE transformChatPhotoDeletedMessage(TelegramChatPhotoDeletedMessage chatPhotoDeletedMessage) {
		return transformMessage(chatPhotoDeletedMessage);
	}

	@Override
	public RETURN_TYPE transformChatCreatedMessage(TelegramChatCreatedMessage chatCreatedMessage) {
		return transformMessage(chatCreatedMessage);
	}

	@Override
	public RETURN_TYPE transformChatMigratedToSupergroupMessage(
			TelegramChatMigratedToSupergroupMessage chatMigratedToSupergroupMessage) {
		return transformMessage(chatMigratedToSupergroupMessage);
	}

	@Override
	public RETURN_TYPE transformChatMigratedFromGroupMessage(
			TelegramChatMigratedFromGroupMessage chatMigratedFromGroupMessage) {
		return transformMessage(chatMigratedFromGroupMessage);
	}

	@Override
	public RETURN_TYPE transformUnknownMessage(TelegramUnknownMessage unknownMessage) {
		return transformMessage(unknownMessage);
	}

	public RETURN_TYPE transformMessage(TelegramMessage message) {
		throw new UnsupportedOperationException("Unhandled message: " + message);
	}

}
