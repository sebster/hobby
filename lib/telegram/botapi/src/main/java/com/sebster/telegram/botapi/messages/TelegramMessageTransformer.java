package com.sebster.telegram.botapi.messages;

public interface TelegramMessageTransformer<RESULT_TYPE> {

	RESULT_TYPE transformTextMessage(TelegramTextMessage message);

	RESULT_TYPE transformAudioMessage(TelegramAudioMessage message);

	RESULT_TYPE transformContactMessage(TelegramContactMessage message);

	RESULT_TYPE transformDocumentMessage(TelegramDocumentMessage message);

	RESULT_TYPE transformPhotoMessage(TelegramPhotoMessage message);

	RESULT_TYPE transformStickerMessage(TelegramStickerMessage message);

	RESULT_TYPE transformVoiceMessage(TelegramVoiceMessage message);

	RESULT_TYPE transformVideoMessage(TelegramVideoMessage message);

	RESULT_TYPE transformLocationMessage(TelegramLocationMessage message);

	RESULT_TYPE transformUserJoinedChatMessage(TelegramUserJoinedChatMessage message);

	RESULT_TYPE transformUserLeftChatMessage(TelegramUserLeftChatMessage message);

	RESULT_TYPE transformChatTitleChangedMessage(TelegramChatTitleChangedMessage message);

	RESULT_TYPE transformChatPhotoChangedMessage(TelegramChatPhotoChangedMessage message);

	RESULT_TYPE transformChatPhotoDeletedMessage(TelegramChatPhotoDeletedMessage message);

	RESULT_TYPE transformChatCreatedMessage(TelegramChatCreatedMessage message);

	RESULT_TYPE transformChatMigratedToSupergroupMessage(TelegramChatMigratedToSupergroupMessage message);

	RESULT_TYPE transformChatMigratedFromGroupMessage(TelegramChatMigratedFromGroupMessage message);

	RESULT_TYPE transformUnknownMessage(TelegramUnknownMessage unknownMessage);

}
