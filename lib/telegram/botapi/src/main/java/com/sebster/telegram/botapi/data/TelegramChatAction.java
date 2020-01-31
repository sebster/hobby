package com.sebster.telegram.botapi.data;

/**
 * Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo
 * for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files, upload_document for general
 * files, find_location for location data, record_video_note or upload_video_note for video notes.
 */
public enum TelegramChatAction {

	TYPING,
	UPLOAD_PHOTO,
	RECORD_VIDEO,
	UPLOAD_VIDEO,
	RECORD_AUDIO,
	UPLOAD_AUDIO,
	UPLOAD_DOCUMENT,
	FIND_LOCATION,
	RECORD_VIDEO_NOTE,
	UPLOAD_VIDEO_NOTE

}
