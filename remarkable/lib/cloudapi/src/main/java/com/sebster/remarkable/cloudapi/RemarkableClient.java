package com.sebster.remarkable.cloudapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.UUID;

import lombok.NonNull;

public interface RemarkableClient {

	UUID getId();

	String getDescription();

	RemarkableRootFolder list();

	default RemarkableItem list(@NonNull UUID id) {
		return list().getItem(id);
	}

	default RemarkableItem list(@NonNull RemarkablePath path) {
		return list().getItem(path);
	}

	default InputStream download(@NonNull RemarkableItem item) {
		return download(item.getDownloadLink().orElseThrow(() -> new IllegalArgumentException("Download link unknown: " + item)));
	}

	default InputStream download(@NonNull RemarkableDownloadLink link) {
		try {
			return link.getUrl().openStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}