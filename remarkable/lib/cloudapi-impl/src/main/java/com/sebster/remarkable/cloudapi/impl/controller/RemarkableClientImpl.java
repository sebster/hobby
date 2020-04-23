package com.sebster.remarkable.cloudapi.impl.controller;

import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import com.sebster.remarkable.cloudapi.RemarkableRootFolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class RemarkableClientImpl implements RemarkableClient {

	@Getter
	private final @NonNull RemarkableClientInfo info;

	private final @NonNull RemarkableApiClient apiClient;

	@Override
	public UUID getId() {
		return info.getClientId();
	}

	@Override
	public String getDescription() {
		return info.getDescription();
	}

	@Override
	public RemarkableRootFolder list() {
		String sessionToken = apiClient.login(info.getLoginToken());
		return new ItemInfoDtoListUnmarshaller(apiClient.list(sessionToken, true)).unmarshal();
	}

	@Override
	public RemarkableDownloadLink downloadLink(UUID id) {
		String sessionToken = apiClient.login(info.getLoginToken());
		ItemInfoDto itemInfo = apiClient.list(sessionToken, id, true);
		return itemInfo.getDownloadLink().orElseThrow(() -> new IllegalStateException("Download link unavailable."));
	}

	@Override
	public RemarkableDownloadLink downloadLink(RemarkablePath path) {
		return downloadLink(list().getItem(path).getId());
	}

	@Override
	public String toString() {
		return info.getDescription() + " (" + info.getClientId() + ")";
	}

}
