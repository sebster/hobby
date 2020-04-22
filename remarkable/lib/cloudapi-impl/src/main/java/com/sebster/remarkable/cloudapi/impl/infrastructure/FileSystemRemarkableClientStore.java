package com.sebster.remarkable.cloudapi.impl.infrastructure;

import static java.util.Collections.emptyList;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableClientDescriptor;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableClientStore;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FileSystemRemarkableClientStore implements RemarkableClientStore {

	private final @NonNull File clients;
	private final @NonNull ObjectMapper mapper;

	@Override
	public List<RemarkableClientDescriptor> loadClientDescriptors() {
		if (!clients.exists()) {
			return emptyList();
		}
		try {
			return mapper.readValue(clients, getClientDescriptorListType());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void addClientDescriptor(@NonNull RemarkableClientDescriptor clientDescriptor) {
		List<RemarkableClientDescriptor> clientDescriptors = new ArrayList<>(loadClientDescriptors());
		if (clientExists(clientDescriptor.getClientId(), clientDescriptors)) {
			throw new IllegalArgumentException("Duplicate client with id: " + clientDescriptor.getClientId());
		}
		clientDescriptors.add(clientDescriptor);
		saveClientDescriptors(clientDescriptors);
	}

	@Override
	public void removeClientDescriptor(@NonNull UUID clientId) {
		List<RemarkableClientDescriptor> clientDescriptors = new ArrayList<>(loadClientDescriptors());
		if (!clientExists(clientId, clientDescriptors)) {
			throw new IllegalArgumentException("No client with id: " + clientId);
		}
		clientDescriptors.removeIf(descriptor -> Objects.equals(descriptor.getClientId(), clientId));
		saveClientDescriptors(clientDescriptors);
	}

	private boolean clientExists(@NonNull UUID clientId, List<RemarkableClientDescriptor> clientDescriptors) {
		return clientDescriptors.stream().anyMatch(descriptor -> Objects.equals(descriptor.getClientId(), clientId));
	}

	private CollectionType getClientDescriptorListType() {
		return mapper.getTypeFactory().constructCollectionType(List.class, RemarkableClientDescriptor.class);
	}

	private void saveClientDescriptors(List<RemarkableClientDescriptor> clientDescriptors) {
		try {
			mapper.writeValue(clients, clientDescriptors);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
