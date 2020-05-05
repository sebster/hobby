package com.sebster.commons.web.springboot.httpclient;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class HttpClientRestTemplateCustomizer implements RestTemplateCustomizer {

	private List<HttpClientBuilderCustomizer> httpClientBuilderCustomizers;

	@Override
	public void customize(@NonNull RestTemplate restTemplate) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		if (httpClientBuilderCustomizers != null) {
			httpClientBuilderCustomizers.forEach(customizer -> customizer.customize(httpClientBuilder));
		}
		HttpClient httpClient = httpClientBuilder.build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
	}

	@Autowired(required = false)
	public void setHttpClientBuilderCustomizers(@NonNull List<HttpClientBuilderCustomizer> httpClientBuilderCustomizers) {
		this.httpClientBuilderCustomizers = List.copyOf(httpClientBuilderCustomizers);
	}

}
