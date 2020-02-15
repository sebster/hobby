package com.sebster.telegram.botapi.impl.dto.methods;

import lombok.Data;

@Data
public abstract class TelegramResponseDto<RESULT> {

	boolean ok;
	RESULT result;
	int errorCode;
	String description;

}
