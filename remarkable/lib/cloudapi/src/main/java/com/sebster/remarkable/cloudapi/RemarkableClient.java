package com.sebster.remarkable.cloudapi;

import java.util.UUID;

public interface RemarkableClient {

	RemarkableClientInfo getInfo();

	RemarkableRootFolder list();

	default RemarkableItem list(UUID id) {
		return list().getItem(id);
	}

	default RemarkableItem list(RemarkablePath path) {
		return list().getItem(path);
	}

	RemarkableDownloadLink downloadLink(UUID id);

	RemarkableDownloadLink downloadLink(RemarkablePath path);

	default UUID getId() {
		return getInfo().getClientId();
	}

	default String getDescription() {
		return getInfo().getDescription();
	}

}