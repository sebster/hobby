package com.sebster.commons.web.springboot.httpclient;

import java.net.ProxySelector;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
@ConditionalOnProperty(prefix = "web.client", name = "proxy", havingValue = "system", matchIfMissing = true)
public class SystemProxyHttpClientBuilderCustomizer implements HttpClientBuilderCustomizer {

	@Override
	public void customize(@NonNull HttpClientBuilder builder) {
		builder.setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault()));
	}

}
