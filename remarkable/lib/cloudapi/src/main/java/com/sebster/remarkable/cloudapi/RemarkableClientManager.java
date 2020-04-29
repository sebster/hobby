package com.sebster.remarkable.cloudapi;

import static com.sebster.commons.strings.Strings.containsIgnoreCase;
import static com.sebster.commons.strings.Strings.startsWithIgnoreCase;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
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
	 * Get a client by its index in the list, the start of its id, or part of its description.
	 * Throws an illegal argument exception if no clients match, or if multiple clients match.
	 */
	default RemarkableClient getClient(@NonNull String selector) {
		return findClient(selector).orElseThrow(() -> new IllegalArgumentException("No such client: " + selector));
	}

	/**
	 * Find a client by part of its description or the start of its id (case insensitive).
	 * Returns an empty optional if no or if multiple clients match.
	 */
	default Optional<RemarkableClient> findClient(@NonNull String selector) {
		List<RemarkableClient> clients = listClients();

		// Can the selector be interpreted as the part of a client description?
		var byDescription = clients.stream()
				.filter(client -> containsIgnoreCase(client.getDescription(), selector))
				.collect(toList());
		if (byDescription.size() == 1) {
			return Optional.of(byDescription.get(0));
		}

		// Can the selector be interpreted as the start of a client id?
		var byId = clients.stream()
				.filter(client -> startsWithIgnoreCase(client.getId().toString(), selector))
				.collect(toList());
		if (byId.size() == 1) {
			return Optional.of(byId.get(0));
		}

		// No match.
		return Optional.empty();
	}

	/**
	 * Unregister the specified client.
	 */
	void unregister(@NonNull RemarkableClient client);

}
