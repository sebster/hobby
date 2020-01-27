package com.sebster.telegram.impl.dto.methods;

import lombok.Data;

@Data
public abstract class BaseTelegramResponseDto<RESULT> {

	boolean ok;
	RESULT result;
	int errorCode;
	String description;

}
