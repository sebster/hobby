package com.sebster.remarkable.cloudapi.impl.infrastructure;

import static java.util.Collections.emptyList;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableClientInfo;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableClientStore;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FileSystemRemarkableClientStore implements RemarkableClientStore {

	private final @NonNull File clientsFile;
	private final @NonNull ObjectMapper mapper;

	@Override
	public List<RemarkableClientInfo> loadClients() {
		if (!clientsFile.exists()) {
			return emptyList();
		}
		try {
			CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, RemarkableClientInfo.class);
			return mapper.readValue(clientsFile, type);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void addClient(@NonNull RemarkableClientInfo clientInfo) {
		ensureClientsFileExistsAndHasCorrectPermissions();
		List<RemarkableClientInfo> clientInfos = new ArrayList<>(loadClients());
		if (clientExists(clientInfo.getClientId(), clientInfos)) {
			throw new IllegalArgumentException("Duplicate client with id: " + clientInfo.getClientId());
		}
		clientInfos.add(clientInfo);
		saveClientInfos(clientInfos);
	}

	@Override
	public void removeClient(@NonNull UUID clientId) {
		List<RemarkableClientInfo> clientInfos = new ArrayList<>(loadClients());
		if (!clientExists(clientId, clientInfos)) {
			throw new IllegalArgumentException("No client with id: " + clientId);
		}
		clientInfos.removeIf(clientInfo -> Objects.equals(clientInfo.getClientId(), clientId));
		saveClientInfos(clientInfos);
	}

	private boolean clientExists(UUID clientId, List<RemarkableClientInfo> clientInfos) {
		return clientInfos.stream().anyMatch(clientInfo -> Objects.equals(clientInfo.getClientId(), clientId));
	}

	private void saveClientInfos(List<RemarkableClientInfo> clientInfos) {
		try {
			mapper.writeValue(clientsFile, clientInfos);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void ensureClientsFileExistsAndHasCorrectPermissions() {
		try {
			Set<PosixFilePermission> ownerReadWrite = PosixFilePermissions.fromString("rw-------");
			if (!clientsFile.exists()) {
				FileAttribute<?> permissions = PosixFilePermissions.asFileAttribute(ownerReadWrite);
				Files.createFile(clientsFile.toPath(), permissions);
			} else {
				Files.setPosixFilePermissions(clientsFile.toPath(), ownerReadWrite);
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
