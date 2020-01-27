package com.sebster.telegram.api.messages;

public interface TelegramMessageVisitor {

	void visitTextMessage(TelegramTextMessage message);

	void visitAudioMessage(TelegramAudioMessage message);

	void visitContactMessage(TelegramContactMessage message);

	void visitDocumentMessage(TelegramDocumentMessage message);

	void visitPhotoMessage(TelegramPhotoMessage message);

	void visitStickerMessage(TelegramStickerMessage message);

	void visitVoiceMessage(TelegramVoiceMessage message);

	void visitVideoMessage(TelegramVideoMessage message);

	void visitLocationMessage(TelegramLocationMessage message);

	void visitUserJoinedChatMessage(TelegramUserJoinedChatMessage message);

	void visitUserLeftChatMessage(TelegramUserLeftChatMessage message);

	void visitChatTitleChangedMessage(TelegramChatTitleChangedMessage message);

	void visitChatPhotoChangedMessage(TelegramChatPhotoChangedMessage message);

	void visitChatPhotoDeletedMessage(TelegramChatPhotoDeletedMessage message);

	void visitChatCreatedMessage(TelegramChatCreatedMessage message);

	void visitChatMigratedToSupergroupMessage(TelegramChatMigratedToSupergroupMessage message);

	void visitChatMigratedFromGroupMessage(TelegramChatMigratedFromGroupMessage message);

	void visitUnknownMessage(TelegramUnknownMessage message);

}
