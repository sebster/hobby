package com.sebster.telegram.botapi.impl.dto.data;

import com.sebster.telegram.botapi.data.TelegramVideo;
import lombok.Data;

@Data
public class TelegramVideoDto {

	String fileId;
	String fileUniqueId;
	int width;
	int height;
	int duration;
	TelegramPhotoSizeDto thumb;
	String mimeType;
	Integer fileSize;

	public TelegramVideo toTelegramVideo() {
		return new TelegramVideo(
				fileId,
				fileUniqueId,
				width,
				height,
				duration,
				thumb != null ? thumb.toTelegramPhoto() : null,
				mimeType,
				fileSize
		);
	}

}
