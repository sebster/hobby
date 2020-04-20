package com.sebster.remarkable.cloudapi.impl.controller;

import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientInfo;
import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import com.sebster.remarkable.cloudapi.RemarkableRootFolder;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RemarkableClientImpl implements RemarkableClient {

	private final @NonNull RemarkableClientInfo clientInfo;
	private final @NonNull RemarkableApiClient apiClient;

	@Override
	public UUID getId() {
		return clientInfo.getClientId();
	}

	@Override
	public RemarkableRootFolder list() {
		String sessionToken = apiClient.login(clientInfo.getLoginToken());
		return new ItemInfoDtoListUnmarshaller(apiClient.list(sessionToken, true)).unmarshal();
	}

	@Override
	public RemarkableDownloadLink downloadLink(UUID id) {
		String sessionToken = apiClient.login(clientInfo.getLoginToken());
		ItemInfoDto itemInfo = apiClient.list(sessionToken, id, true);
		return itemInfo.getDownloadLink().orElseThrow(() -> new IllegalStateException("Download link unavailable."));
	}

	@Override
	public RemarkableDownloadLink downloadLink(RemarkablePath path) {
		return downloadLink(list().getItem(path).getId());
	}

}
