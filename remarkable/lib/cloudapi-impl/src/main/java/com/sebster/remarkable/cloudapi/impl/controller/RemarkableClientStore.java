package com.sebster.remarkable.cloudapi.impl.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableException;
import lombok.NonNull;

public interface RemarkableClientStore {

	List<RemarkableClientInfo> loadClients();

	default RemarkableClientInfo loadClient(UUID clientId) {
		return loadClients().stream()
				.filter(client -> Objects.equals(client.getClientId(), clientId))
				.findFirst()
				.orElseThrow(() -> new RemarkableException("No client with id: " + clientId));
	}

	void addClient(@NonNull RemarkableClientInfo clientInfo);

	void removeClient(@NonNull UUID clientId);

}
