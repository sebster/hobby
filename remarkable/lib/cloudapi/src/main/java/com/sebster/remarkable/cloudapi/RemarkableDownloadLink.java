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
	Instant expirationTime;

	public Optional<Instant> getExpirationTime() {
		return Optional.ofNullable(expirationTime);
	}

}
