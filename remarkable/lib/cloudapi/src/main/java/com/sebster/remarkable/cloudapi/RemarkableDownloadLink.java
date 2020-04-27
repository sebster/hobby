package com.sebster.remarkable.cloudapi;

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

}
