package com.sebster.remarkable.cloudapi.impl.controller;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientInfo;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RemarkableClientManagerImpl implements RemarkableClientManager {

	public static final String WINDOWS_CLIENT_TYPE = "desktop-windows";

	private final @NonNull RemarkableClientStore clientStore;
	private final @NonNull RemarkableApiClient apiClient;

	@Override
	public UUID register(@NonNull String code, String description) {
		UUID clientId = randomUUID();
		String loginToken = apiClient.register(clientId, WINDOWS_CLIENT_TYPE, code);
		clientStore.addClientDescriptor(new RemarkableClientDescriptor(clientId, description, loginToken));
		return clientId;
	}

	@Override
	public List<RemarkableClientInfo> listClients() {
		return clientStore.loadClientDescriptors().stream().map(RemarkableClientDescriptor::toInfo).collect(toList());
	}

	@Override
	public RemarkableClient getClient(@NonNull UUID clientId) {
		RemarkableClientDescriptor clientDescriptor = clientStore.loadClientDescriptor(clientId);
		return new RemarkableClientImpl(clientDescriptor.toInfo(), clientDescriptor.getLoginToken(), apiClient);
	}

	@Override
	public void unregister(@NonNull UUID clientId) {
		RemarkableClientDescriptor clientDescriptor = clientStore.loadClientDescriptor(clientId);
		apiClient.unregister(clientDescriptor.getLoginToken());
		clientStore.removeClientDescriptor(clientId);
	}

}
