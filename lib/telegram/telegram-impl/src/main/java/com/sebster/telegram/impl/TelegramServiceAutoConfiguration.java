package com.sebster.telegram.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sebster.telegram.api.TelegramService;

@Configuration
@EnableConfigurationProperties(TelegramProperties.class)
public class TelegramServiceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public TelegramService telegramService(TelegramProperties properties, RestTemplateBuilder restTemplateBuilder) {
		return new TelegramServiceImpl(properties.getAuthKey(), restTemplateBuilder);
	}

}
