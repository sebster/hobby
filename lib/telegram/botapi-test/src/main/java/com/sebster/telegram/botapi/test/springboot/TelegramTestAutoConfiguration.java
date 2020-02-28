package com.sebster.telegram.botapi.test.springboot;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.sebster.commons.clock.ClockService;
import com.sebster.telegram.botapi.test.TelegramServiceStub;
import com.sebster.telegram.botapi.test.TelegramStub;

@TestConfiguration
public class TelegramTestAutoConfiguration {

	@Bean
	public TelegramServiceStub telegramService(ClockService clockService) {
		return new TelegramServiceStub(clockService);
	}

	@Bean
	public TelegramStub telegram(TelegramServiceStub telegramServiceStub) {
		return new TelegramStub(telegramServiceStub);
	}

}
