package com.sebster.telegram.botapi.impl.dto.data;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.sebster.telegram.botapi.data.TelegramPhoto;
import com.sebster.telegram.botapi.data.TelegramPhotoList;
import lombok.Data;

@Data
public final class TelegramPhotoSizeDto {

	String fileId;
	String fileUniqueId;
	int width;
	int height;
	Integer fileSize;

	public TelegramPhoto toTelegramPhoto() {
		return new TelegramPhoto(fileId, fileUniqueId, width, height, fileSize);
	}

	public static TelegramPhotoList toTelegramPhotoList(List<TelegramPhotoSizeDto> photos) {
		return new TelegramPhotoList(photos.stream().map(TelegramPhotoSizeDto::toTelegramPhoto).collect(toList()));
	}

}
