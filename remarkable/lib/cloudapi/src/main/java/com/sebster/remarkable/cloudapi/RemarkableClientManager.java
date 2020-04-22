package com.sebster.remarkable.cloudapi;

import java.util.List;
import java.util.UUID;

import lombok.NonNull;

public interface RemarkableClientManager {

	/**
	 * Register a client and return the new client id.
	 */
	UUID register(@NonNull String code, String description);

	/**
	 * List the client known by this client manager.
	 */
	List<RemarkableClientInfo> listClients();

	/**
	 * Get the specified client.
	 */
	RemarkableClient getClient(@NonNull UUID id);

	/**
	 * Unregister the client with the specified id.
	 */
	void unregister(@NonNull UUID id);

}
