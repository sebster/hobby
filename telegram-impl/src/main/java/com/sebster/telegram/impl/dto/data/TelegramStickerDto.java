package com.sebster.telegram.impl.dto.data;

import com.sebster.telegram.api.data.TelegramSticker;
import lombok.Value;

@Value
public class TelegramStickerDto {

	String fileId;
	String fileUniqueId;
	int width;
	int height;
	TelegramPhotoSizeDto thumb;
	Integer fileSize;

	public TelegramSticker toTelegramSticker() {
		return new TelegramSticker(fileId, fileUniqueId, width, height, thumb != null ? thumb.toTelegramPhoto() : null, fileSize);
	}

}
