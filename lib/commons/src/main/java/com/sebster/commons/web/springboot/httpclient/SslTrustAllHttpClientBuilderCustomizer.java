package com.sebster.commons.web.springboot.httpclient;

import java.security.GeneralSecurityException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
@ConditionalOnProperty(prefix = "web.client.ssl", name = "verify", havingValue = "false")
public class SslTrustAllHttpClientBuilderCustomizer implements HttpClientBuilderCustomizer {

	@Override
	public void customize(@NonNull HttpClientBuilder builder) {
		try {
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustAllStrategy()).build();
			HostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			builder.setSSLSocketFactory(sslSocketFactory);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
