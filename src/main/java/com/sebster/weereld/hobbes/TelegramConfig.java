package com.sebster.weereld.hobbes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sebster.telegram.api.TelegramService;
import com.sebster.telegram.impl.TelegramServiceImpl;

@Configuration
public class TelegramConfig {

	@Value("${telegram.auth-key}")
	private String token;

	@Bean
	public TelegramService telegramService() {
		return new TelegramServiceImpl(token);
	}

}
