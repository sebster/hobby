package com.sebster.remarkable.cloudapi.impl;

import static java.util.Collections.singletonList;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientFactory;
import com.sebster.remarkable.cloudapi.RemarkableClientInfo;
import com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableApiClient;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableClientFactoryImpl;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableClientImpl;
import com.sebster.remarkable.cloudapi.impl.infrastructure.http.ItemInfoDtoJsonMapping;
import com.sebster.remarkable.cloudapi.impl.infrastructure.http.RemarkableApiClientImpl;

@ComponentScan(basePackageClasses = RemarkableCloudApiAutoConfiguration.class)
@EnableConfigurationProperties(RemarkableClientProperties.class)
public class RemarkableCloudApiAutoConfiguration {

	@Bean
	public RemarkableApiClient remarkableApiClient(
			Jackson2ObjectMapperBuilder objectMapperBuilder,
			RestTemplateBuilder restTemplateBuilder
	) {
		ObjectMapper objectMapper = objectMapperBuilder.mixIn(ItemInfoDto.class, ItemInfoDtoJsonMapping.class).build();
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
		messageConverter.setSupportedMediaTypes(singletonList(new MediaType("text", "plain")));
		RestTemplate restTemplate = restTemplateBuilder.additionalMessageConverters(messageConverter).build();
		return new RemarkableApiClientImpl(restTemplate);
	}

	@Bean
	public RemarkableClientFactory remarkableClientFactory(RemarkableApiClient remarkableApiClient) {
		return new RemarkableClientFactoryImpl(remarkableApiClient);
	}

	@Bean
	@ConditionalOnProperty(prefix = "remarkable", name = "client-id")
	public RemarkableClientInfo remarkableClientInfo(RemarkableClientProperties properties) {
		return new RemarkableClientInfo(properties.getClientId(), properties.getLoginToken());
	}

	@Bean
	@ConditionalOnBean(RemarkableClientInfo.class)
	public RemarkableClient remarkableClient(RemarkableClientInfo remarkableClientInfo, RemarkableApiClient remarkableApiClient) {
		return new RemarkableClientImpl(remarkableClientInfo, remarkableApiClient);
	}

}
