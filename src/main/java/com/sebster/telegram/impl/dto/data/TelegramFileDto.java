package com.sebster.telegram.impl.dto.data;

import java.net.MalformedURLException;
import java.net.URL;

import com.sebster.telegram.api.data.TelegramFileLink;
import lombok.Data;

@Data
public class TelegramFileDto {

	String fileId;
	String fileUniqueId;
	Integer fileSize;
	String filePath;

	public TelegramFileLink toTelegramFileLink(URL baseUrl) throws MalformedURLException {
		URL fileUrl = filePath != null ? new URL(baseUrl, filePath) : null;
		return new TelegramFileLink(fileId, fileUniqueId, fileSize, filePath, fileUrl);
	}

}
