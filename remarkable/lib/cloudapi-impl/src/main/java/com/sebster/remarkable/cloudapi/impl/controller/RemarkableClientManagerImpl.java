package com.sebster.remarkable.cloudapi.impl.controller;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import com.sebster.remarkable.cloudapi.RemarkableException;
import lombok.NonNull;

public class RemarkableClientManagerImpl implements RemarkableClientManager {

	public static final String WINDOWS_CLIENT_TYPE = "desktop-windows";

	private final @NonNull Clock clock;
	private final @NonNull RemarkableClientStore clientStore;
	private final @NonNull RemarkableApiClient apiClient;

	private final Map<RemarkableClientInfo, RemarkableClient> clientCache = new ConcurrentHashMap<>();

	public RemarkableClientManagerImpl(
			@NonNull Clock clock,
			@NonNull RemarkableClientStore clientStore,
			@NonNull RemarkableApiClient apiClient
	) {
		this.clock = clock;
		this.clientStore = clientStore;
		this.apiClient = apiClient;
	}

	@Override
	public RemarkableClient register(@NonNull String code, @NonNull String description) {
		if (findClient(description).isPresent()) {
			throw new RemarkableException("Duplicate client: " + description);
		}
		UUID clientId = randomUUID();
		String loginToken = apiClient.register(clientId, WINDOWS_CLIENT_TYPE, code);
		RemarkableClientInfo clientInfo = new RemarkableClientInfo(clientId, description, loginToken);
		clientStore.addClient(clientInfo);
		return createClient(clientInfo);
	}

	@Override
	public List<RemarkableClient> listClients() {
		return clientStore.loadClients().stream().map(this::createClient).collect(toList());
	}

	@Override
	public RemarkableClient getClient(@NonNull UUID clientId) {
		return createClient(clientStore.loadClient(clientId));
	}

	@Override
	public void unregister(@NonNull RemarkableClient client) {
		RemarkableClientInfo clientInfo = clientStore.loadClient(client.getId());
		clientCache.remove(clientInfo);
		apiClient.unregister(clientInfo.getLoginToken());
		clientStore.removeClient(client.getId());
	}

	private RemarkableClient createClient(RemarkableClientInfo clientInfo) {
		return clientCache.computeIfAbsent(clientInfo, key -> new RemarkableClientImpl(clock, clientInfo, apiClient));
	}

}
