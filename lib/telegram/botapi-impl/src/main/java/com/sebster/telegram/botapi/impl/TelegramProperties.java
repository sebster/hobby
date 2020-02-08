package com.sebster.telegram.botapi.impl;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "telegram")
@Validated
@Data
@NoArgsConstructor
public class TelegramProperties {

	@NotBlank(message = "must contain a valid telegram API token")
	private String authKey;

}
