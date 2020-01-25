package com.sebster.telegram.api.data.messages;

public interface TelegramMessageVisitor {

	void visitTextMessage(TelegramTextMessage textMessage);

	void visitAudioMessage(TelegramAudioMessage audioMessage);

	void visitContactMessage(TelegramContactMessage contactMessage);

	void visitDocumentMessage(TelegramDocumentMessage documentMessage);

	void visitPhotoMessage(TelegramPhotoMessage photoMessage);

	void visitStickerMessage(TelegramStickerMessage stickerMessage);

	void visitVoiceMessage(TelegramVoiceMessage voiceMessage);

	void visitVideoMessage(TelegramVideoMessage videoMessage);

	void visitLocationMessage(TelegramLocationMessage locationMessage);

	void visitUserJoinedChatMessage(TelegramUserJoinedChatMessage userJoinedChatMessage);

	void visitUserLeftChatMessage(TelegramUserLeftChatMessage userLeftChatMessage);

	void visitChatTitleChangedMessage(TelegramChatTitleChangedMessage chatTitleChangedMessage);

	void visitChatPhotoChangedMessage(TelegramChatPhotoChangedMessage chatPhotoChangedMessage);

	void visitChatPhotoDeletedMessage(TelegramChatPhotoDeletedMessage chatPhotoDeletedMessage);

	void visitChatCreatedMessage(TelegramChatCreatedMessage chatCreatedMessage);

	void visitChatMigratedToSupergroupMessage(TelegramChatMigratedToSupergroupMessage chatMigratedToSupergroupMessage);

	void visitChatMigratedFromGroupMessage(TelegramChatMigratedFromGroupMessage chatMigratedFromGroupMessage);

}
