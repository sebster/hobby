package com.sebster.remarkable.cloudapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class RemarkableDownloadLink {

	@NonNull UUID itemId;
	@NonNull URI url;
	Instant expiration;

	public Optional<Instant> getExpiration() {
		return Optional.ofNullable(expiration);
	}

	public InputStream download() {
		try {
			return url.toURL().openStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
