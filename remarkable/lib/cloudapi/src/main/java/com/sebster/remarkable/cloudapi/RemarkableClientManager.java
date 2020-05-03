package com.sebster.remarkable.cloudapi;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import lombok.NonNull;

public interface RemarkableClientManager {

	/**
	 * Register and return a new client.
	 *
	 * @throws DuplicateClientException if a client with that description already exists
	 */
	RemarkableClient register(@NonNull String code, String description);

	/**
	 * List the client known by this client manager.
	 */
	List<RemarkableClient> listClients();

	/**
	 * Get the client with the specified id.
	 */
	RemarkableClient getClient(@NonNull UUID id);

	/**
	 * Get a client by its description.
	 */
	default RemarkableClient getClient(@NonNull String description) {
		return findClient(description).orElseThrow(() -> new IllegalArgumentException("No such client: " + description));
	}

	/**
	 * Find a client its description.
	 */
	default Optional<RemarkableClient> findClient(@NonNull String description) {
		return listClients().stream().filter(client -> Objects.equals(description, client.getDescription())).findFirst();
	}

	/**
	 * Unregister the specified client.
	 */
	void unregister(@NonNull RemarkableClient client);

}
