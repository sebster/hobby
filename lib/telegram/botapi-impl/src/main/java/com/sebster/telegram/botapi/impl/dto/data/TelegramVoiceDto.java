package com.sebster.telegram.botapi.impl.dto.data;

import com.sebster.telegram.botapi.data.TelegramVoice;
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
