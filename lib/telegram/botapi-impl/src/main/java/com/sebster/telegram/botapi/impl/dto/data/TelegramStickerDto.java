package com.sebster.telegram.botapi.impl.dto.data;

import com.sebster.telegram.botapi.data.TelegramSticker;
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
