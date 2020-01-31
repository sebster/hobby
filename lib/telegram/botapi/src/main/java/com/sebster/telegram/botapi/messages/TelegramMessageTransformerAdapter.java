package com.sebster.telegram.botapi.messages;

public class TelegramMessageTransformerAdapter<RETURN_TYPE> implements TelegramMessageTransformer<RETURN_TYPE> {

	@Override
	public RETURN_TYPE transformTextMessage(TelegramTextMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformAudioMessage(TelegramAudioMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformContactMessage(TelegramContactMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformDocumentMessage(TelegramDocumentMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformPhotoMessage(TelegramPhotoMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformStickerMessage(TelegramStickerMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformVoiceMessage(TelegramVoiceMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformVideoMessage(TelegramVideoMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformLocationMessage(TelegramLocationMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformUserJoinedChatMessage(TelegramUserJoinedChatMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformUserLeftChatMessage(TelegramUserLeftChatMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformChatTitleChangedMessage(TelegramChatTitleChangedMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformChatPhotoChangedMessage(TelegramChatPhotoChangedMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformChatPhotoDeletedMessage(TelegramChatPhotoDeletedMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformChatCreatedMessage(TelegramChatCreatedMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformChatMigratedToSupergroupMessage(TelegramChatMigratedToSupergroupMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformChatMigratedFromGroupMessage(TelegramChatMigratedFromGroupMessage message) {
		return transformMessage(message);
	}

	@Override
	public RETURN_TYPE transformUnknownMessage(TelegramUnknownMessage message) {
		return transformMessage(message);
	}

	public RETURN_TYPE transformMessage(TelegramMessage message) {
		throw new UnsupportedOperationException("Unhandled message: " + message);
	}

}
