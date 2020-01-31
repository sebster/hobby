package com.sebster.telegram.botapi.impl;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "telegram")
@Data
@NoArgsConstructor
public class TelegramProperties {

	@NotNull
	private String authKey;

}
