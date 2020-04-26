package com.sebster.remarkable.cloudapi.impl.controller;

import java.util.List;
import java.util.UUID;

import lombok.NonNull;

public interface RemarkableApiClient {

	/**
	 * Register a new client with the specified client id and client type. Returns a login token that can be used to log in.
	 */
	String register(@NonNull UUID clientId, @NonNull String clientType, @NonNull String code);

	/**
	 * Unregister the client with the specified login code.
	 */
	void unregister(@NonNull String loginToken);

	/**
	 * Log the client in. Returns a session token that can be used for subsequent API calls.
	 */
	String login(@NonNull String loginToken);

	/**
	 * List all files on the reMarkable. You can choose whether to include a download link. Note that the link expires.
	 */
	List<ItemInfoDto> list(@NonNull String sessionToken, boolean includeBlobUrl);

	/**
	 * List the specified file on the reMarkable. You can choose whether to include a download link. Note that the link expires.
	 */
	ItemInfoDto list(@NonNull String sessionToken, @NonNull UUID id, boolean includeBlobUrl);

	/**
	 * Update the metadata of one or more items on the reMarkable.
	 */
	List<ItemInfoDto> updateMetadata(@NonNull String sessionToken, @NonNull List<ItemInfoDto> itemInfos);

}
