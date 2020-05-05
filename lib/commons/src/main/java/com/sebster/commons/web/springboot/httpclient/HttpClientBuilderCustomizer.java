package com.sebster.commons.web.springboot.httpclient;

import org.apache.http.impl.client.HttpClientBuilder;

import lombok.NonNull;

public interface HttpClientBuilderCustomizer {

	void customize(@NonNull HttpClientBuilder builder);

}
