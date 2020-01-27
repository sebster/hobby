package com.sebster.telegram.impl.dto.data;

import com.sebster.telegram.api.data.TelegramVoice;
import lombok.Data;

@Data
public class TelegramVoiceDto {

	String fileId;
	String fileUniqueId;
	int duration;
	String mimeType;
	Integer fileSize;

	public TelegramVoice toTelegramVoice() {
		return new TelegramVoice(fileId, fileUniqueId, duration, mimeType, fileSize);
	}

}
