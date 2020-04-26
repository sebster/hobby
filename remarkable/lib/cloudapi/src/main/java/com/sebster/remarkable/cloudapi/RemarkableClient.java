package com.sebster.remarkable.cloudapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.UUID;

public interface RemarkableClient {

	UUID getId();

	String getDescription();

	RemarkableRootFolder list();

	default RemarkableItem list(UUID id) {
		return list().getItem(id);
	}

	default RemarkableItem list(RemarkablePath path) {
		return list().getItem(path);
	}

	RemarkableDownloadLink downloadLink(UUID id);

	RemarkableDownloadLink downloadLink(RemarkablePath path);

	default InputStream download(RemarkableDownloadLink link) {
		try {
			return link.getUrl().openStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}