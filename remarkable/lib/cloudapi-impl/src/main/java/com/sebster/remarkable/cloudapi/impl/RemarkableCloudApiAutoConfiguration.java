package com.sebster.remarkable.cloudapi.impl;

import static java.util.Collections.singletonList;

import java.time.Clock;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableApiClient;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableClientManagerImpl;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableClientStore;
import com.sebster.remarkable.cloudapi.impl.infrastructure.FileSystemRemarkableClientStore;
import com.sebster.remarkable.cloudapi.impl.infrastructure.http.RemarkableApiClientImpl;

@ComponentScan(basePackageClasses = RemarkableCloudApiAutoConfiguration.class)
@EnableConfigurationProperties(RemarkableProperties.class)
public class RemarkableCloudApiAutoConfiguration {

	@Bean
	public RemarkableApiClient remarkableApiClient(
			Jackson2ObjectMapperBuilder objectMapperBuilder,
			RestTemplateBuilder restTemplateBuilder
	) {
		ObjectMapper objectMapper = objectMapperBuilder.indentOutput(true).build();
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
		messageConverter.setSupportedMediaTypes(singletonList(new MediaType("text", "plain")));
		RestTemplate restTemplate = restTemplateBuilder.additionalMessageConverters(messageConverter).build();
		return new RemarkableApiClientImpl(restTemplate);
	}

	@Bean
	public RemarkableClientStore remarkableClientStore(
			Jackson2ObjectMapperBuilder objectMapperBuilder,
			RemarkableProperties properties
	) {
		ObjectMapper objectMapper = objectMapperBuilder.indentOutput(true).build();
		return new FileSystemRemarkableClientStore(properties.getClients(), objectMapper);
	}

	@Bean
	public RemarkableClientManager remarkableClientManager(RemarkableClientStore clientStore, RemarkableApiClient apiClient) {
		return new RemarkableClientManagerImpl(Clock.systemDefaultZone(), clientStore, apiClient);
	}

}
