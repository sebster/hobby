package com.sebster.remarkable.cloudapi.impl.controller;

import static java.util.UUID.randomUUID;

import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientFactory;
import com.sebster.remarkable.cloudapi.RemarkableClientInfo;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RemarkableClientFactoryImpl implements RemarkableClientFactory {

	public static final String WINDOWS_CLIENT_TYPE = "desktop-windows";

	private final @NonNull RemarkableApiClient remarkableApiClient;

	@Override
	public RemarkableClientInfo register(@NonNull String code) {
		UUID clientId = randomUUID();
		String loginToken = remarkableApiClient.register(clientId, WINDOWS_CLIENT_TYPE, code);
		return new RemarkableClientInfo(clientId, loginToken);
	}

	@Override
	public RemarkableClient createClient(@NonNull RemarkableClientInfo clientInfo) {
		return new RemarkableClientImpl(clientInfo, remarkableApiClient);
	}

}
