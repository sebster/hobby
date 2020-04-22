package com.sebster.remarkable.cloudapi.impl.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.NonNull;

public interface RemarkableClientStore {

	List<RemarkableClientDescriptor> loadClientDescriptors();

	default RemarkableClientDescriptor loadClientDescriptor(UUID clientId) {
		return loadClientDescriptors().stream()
				.filter(client -> Objects.equals(client.getClientId(), clientId))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("No client with id: " + clientId));
	}

	void addClientDescriptor(@NonNull RemarkableClientDescriptor clientDescriptor);

	void removeClientDescriptor(@NonNull UUID clientId);

}
