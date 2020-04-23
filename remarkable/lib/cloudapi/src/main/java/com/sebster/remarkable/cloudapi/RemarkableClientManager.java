package com.sebster.remarkable.cloudapi;

import java.util.List;
import java.util.UUID;

import lombok.NonNull;

public interface RemarkableClientManager {

	/**
	 * Register and return a new client.
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
	 * Unregister the specified client.
	 */
	void unregister(@NonNull RemarkableClient client);

}
