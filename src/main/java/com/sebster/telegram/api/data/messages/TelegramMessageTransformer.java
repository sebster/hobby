package com.sebster.telegram.api.data.messages;

public interface TelegramMessageTransformer<RESULT_TYPE> {

	RESULT_TYPE transformTextMessage(TelegramTextMessage textMessage);

	RESULT_TYPE transformAudioMessage(TelegramAudioMessage audioMessage);

	RESULT_TYPE transformContactMessage(TelegramContactMessage contactMessage);

	RESULT_TYPE transformDocumentMessage(TelegramDocumentMessage documentMessage);

	RESULT_TYPE transformPhotoMessage(TelegramPhotoMessage photoMessage);

	RESULT_TYPE transformStickerMessage(TelegramStickerMessage stickerMessage);

	RESULT_TYPE transformVoiceMessage(TelegramVoiceMessage voiceMessage);

	RESULT_TYPE transformVideoMessage(TelegramVideoMessage videoMessage);

	RESULT_TYPE transformLocationMessage(TelegramLocationMessage locationMessage);

	RESULT_TYPE transformUserJoinedChatMessage(TelegramUserJoinedChatMessage userJoinedChatMessage);

	RESULT_TYPE transformUserLeftChatMessage(TelegramUserLeftChatMessage userLeftChatMessage);

	RESULT_TYPE transformChatTitleChangedMessage(TelegramChatTitleChangedMessage chatTitleChangedMessage);

	RESULT_TYPE transformChatPhotoChangedMessage(TelegramChatPhotoChangedMessage chatPhotoChangedMessage);

	RESULT_TYPE transformChatPhotoDeletedMessage(TelegramChatPhotoDeletedMessage chatPhotoDeletedMessage);

	RESULT_TYPE transformChatCreatedMessage(TelegramChatCreatedMessage chatCreatedMessage);

	RESULT_TYPE transformChatMigratedToSupergroupMessage(
			TelegramChatMigratedToSupergroupMessage chatMigratedToSupergroupMessage);

	RESULT_TYPE transformChatMigratedFromGroupMessage(
			TelegramChatMigratedFromGroupMessage chatMigratedFromGroupMessage);
	
}
