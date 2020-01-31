package com.sebster.telegram.impl.dto.data;

import com.sebster.telegram.api.data.TelegramDocument;
import lombok.Data;

@Data
public class TelegramDocumentDto {

	String fileId;
	String fileUniqueId;
	TelegramPhotoSizeDto thumb;
	String fileName;
	String mimeType;
	Integer fileSize;

	public TelegramDocument toTelegramDocument() {
		return new TelegramDocument(
				fileId,
				fileUniqueId,
				thumb != null ? thumb.toTelegramPhoto() : null,
				fileName,
				mimeType,
				fileSize
		);
	}

}
