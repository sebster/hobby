package com.sebster.telegram.api.data.messages;

public class TelegramMessageVisitorAdapter implements TelegramMessageVisitor {

	@Override
	public void visitTextMessage(TelegramTextMessage textMessage) {
		visitMessage(textMessage);
	}

	@Override
	public void visitAudioMessage(TelegramAudioMessage audioMessage) {
		visitMessage(audioMessage);
	}

	@Override
	public void visitContactMessage(TelegramContactMessage contactMessage) {
		visitMessage(contactMessage);
	}

	@Override
	public void visitDocumentMessage(TelegramDocumentMessage documentMessage) {
		visitMessage(documentMessage);
	}

	@Override
	public void visitPhotoMessage(TelegramPhotoMessage photoMessage) {
		visitMessage(photoMessage);
	}

	@Override
	public void visitStickerMessage(TelegramStickerMessage stickerMessage) {
		visitMessage(stickerMessage);
	}

	@Override
	public void visitVoiceMessage(TelegramVoiceMessage voiceMessage) {
		visitMessage(voiceMessage);
	}

	@Override
	public void visitVideoMessage(TelegramVideoMessage videoMessage) {
		visitMessage(videoMessage);
	}

	@Override
	public void visitLocationMessage(TelegramLocationMessage locationMessage) {
		visitMessage(locationMessage);
	}

	@Override
	public void visitUserJoinedChatMessage(TelegramUserJoinedChatMessage userJoinedChatMessage) {
		visitMessage(userJoinedChatMessage);
	}

	@Override
	public void visitUserLeftChatMessage(TelegramUserLeftChatMessage userLeftChatMessage) {
		visitMessage(userLeftChatMessage);
	}

	@Override
	public void visitChatTitleChangedMessage(TelegramChatTitleChangedMessage chatTitleChangedMessage) {
		visitMessage(chatTitleChangedMessage);
	}

	@Override
	public void visitChatPhotoChangedMessage(TelegramChatPhotoChangedMessage chatPhotoChangedMessage) {
		visitMessage(chatPhotoChangedMessage);
	}

	@Override
	public void visitChatPhotoDeletedMessage(TelegramChatPhotoDeletedMessage chatPhotoDeletedMessage) {
		visitMessage(chatPhotoDeletedMessage);
	}

	@Override
	public void visitChatCreatedMessage(TelegramChatCreatedMessage chatCreatedMessage) {
		visitMessage(chatCreatedMessage);
	}

	@Override
	public void visitChatMigratedToSupergroupMessage(
			TelegramChatMigratedToSupergroupMessage chatMigratedToSupergroupMessage) {
		visitMessage(chatMigratedToSupergroupMessage);
	}

	@Override
	public void visitChatMigratedFromGroupMessage(TelegramChatMigratedFromGroupMessage chatMigratedFromGroupMessage) {
		visitMessage(chatMigratedFromGroupMessage);
	}

	public void visitMessage(TelegramMessage message) {
		throw new UnsupportedOperationException("Unhandled message: " + message);
	}

}
