package com.sebster.remarkable.cloudapi.impl.controller;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Instant;
import java.util.Optional;

import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import lombok.Value;

@Value
public class ItemInfoDto {

	String id;
	int version;
	String message;
	boolean success;
	String blobUrlGet;
	String blobUrlGetExpires;
	String modifiedClient;
	String type;
	String visibleName;
	int currentPage;
	boolean bookmarked;
	String parent;

	public Optional<RemarkableDownloadLink> getDownloadLink() {
		if (blobUrlGet == null) {
			return Optional.empty();
		}
		try {
			return Optional.of(new RemarkableDownloadLink(
					URI.create(getBlobUrlGet()).toURL(),
					Instant.parse(getBlobUrlGetExpires())
			));
		} catch (MalformedURLException e) {
			throw new UncheckedIOException(e);
		}
	}

}
