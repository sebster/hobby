package com.sebster.telegram.impl.dto.data;

import com.sebster.telegram.api.data.TelegramVideo;
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
