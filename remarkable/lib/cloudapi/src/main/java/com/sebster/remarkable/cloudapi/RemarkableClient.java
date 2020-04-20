package com.sebster.remarkable.cloudapi;

import java.util.UUID;

public interface RemarkableClient {

	UUID getId();

	RemarkableRootFolder list();

	default RemarkableItem list(UUID id) {
		return list().getItem(id);
	}

	default RemarkableItem list(RemarkablePath path) {
		return list().getItem(path);
	}

	RemarkableDownloadLink downloadLink(UUID id);

	RemarkableDownloadLink downloadLink(RemarkablePath path);

}