package com.sebster.telegram.impl.dto.data;

import com.sebster.telegram.api.data.TelegramLocation;
import lombok.Data;

@Data
public class TelegramLocationDto {

	double longitude;
	double latitude;

	public TelegramLocation toTelegramLocation() {
		return new TelegramLocation(longitude, latitude);
	}

}
