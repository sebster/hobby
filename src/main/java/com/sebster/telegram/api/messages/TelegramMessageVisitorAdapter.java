package com.sebster.telegram.api.messages;

public class TelegramMessageVisitorAdapter implements TelegramMessageVisitor {

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitAudioMessage(TelegramAudioMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitContactMessage(TelegramContactMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitDocumentMessage(TelegramDocumentMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitPhotoMessage(TelegramPhotoMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitStickerMessage(TelegramStickerMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitVoiceMessage(TelegramVoiceMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitVideoMessage(TelegramVideoMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitLocationMessage(TelegramLocationMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitUserJoinedChatMessage(TelegramUserJoinedChatMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitUserLeftChatMessage(TelegramUserLeftChatMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitChatTitleChangedMessage(TelegramChatTitleChangedMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitChatPhotoChangedMessage(TelegramChatPhotoChangedMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitChatPhotoDeletedMessage(TelegramChatPhotoDeletedMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitChatCreatedMessage(TelegramChatCreatedMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitChatMigratedToSupergroupMessage(TelegramChatMigratedToSupergroupMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitChatMigratedFromGroupMessage(TelegramChatMigratedFromGroupMessage message) {
		visitMessage(message);
	}

	@Override
	public void visitUnknownMessage(TelegramUnknownMessage message) {
		visitMessage(message);
	}

	public void visitMessage(TelegramMessage message) {
		throw new UnsupportedOperationException("Unhandled message: " + message);
	}

}
