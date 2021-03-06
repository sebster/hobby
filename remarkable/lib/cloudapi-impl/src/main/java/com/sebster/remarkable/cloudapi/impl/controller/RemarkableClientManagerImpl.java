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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemarkableClientManagerImpl implements RemarkableClientManager {

	public static final String WINDOWS_CLIENT_TYPE = "desktop-windows";

	private final @NonNull Clock clock;
	private final @NonNull RemarkableClientStore clientStore;
	private final @NonNull RemarkableApiClient apiClient;

	private final Map<UUID, RemarkableClient> clientCache = new ConcurrentHashMap<>();

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
		clientCache.remove(client.getId());
		apiClient.unregister(clientInfo.getLoginToken());
		clientStore.removeClient(client.getId());
	}

	private RemarkableClient createClient(RemarkableClientInfo clientInfo) {
		return clientCache.computeIfAbsent(clientInfo.getClientId(), key -> new RemarkableClientImpl(clock, clientInfo, apiClient));
	}

}
