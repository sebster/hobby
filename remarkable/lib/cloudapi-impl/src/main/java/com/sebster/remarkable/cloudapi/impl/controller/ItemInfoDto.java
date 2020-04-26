package com.sebster.remarkable.cloudapi.impl.controller;

import static java.time.Instant.EPOCH;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ItemInfoDto {

	public static final String FOLDER_TYPE = "CollectionType";
	public static final String DOCUMENT_TYPE = "DocumentType";

	@NonNull String id;
	int version;

	String message;
	Boolean success;
	String blobUrlGet;
	String blobUrlGetExpires;
	String modifiedClient;
	String type;
	String visibleName;
	Integer currentPage;
	Boolean bookmarked;
	String parent;

	public Optional<String> getMessage() {
		return Optional.ofNullable(message);
	}

	public Optional<Boolean> getSuccess() {
		return Optional.ofNullable(success);
	}

	public boolean isSuccess() {
		return getSuccess().orElse(true);
	}

	public Optional<String> getBlobUrlGet() {
		return Optional.ofNullable(blobUrlGet);
	}

	public Optional<String> getBlobUrlGetExpires() {
		return Optional.ofNullable(blobUrlGetExpires);
	}

	public Optional<RemarkableDownloadLink> getDownloadLink() {
		if (blobUrlGet == null) {
			return Optional.empty();
		}
		try {
			return Optional.of(new RemarkableDownloadLink(
					UUID.fromString(getId()),
					URI.create(blobUrlGet).toURL(),
					getBlobUrlGetExpires().map(Instant::parse).orElse(EPOCH)
			));
		} catch (MalformedURLException e) {
			throw new UncheckedIOException(e);
		}
	}

	public Optional<String> getModifiedClient() {
		return Optional.ofNullable(modifiedClient);
	}

	public Optional<String> getType() {
		return Optional.ofNullable(type);
	}

	public Optional<String> getVisibleName() {
		return Optional.ofNullable(visibleName);
	}

	public Optional<Integer> getCurrentPage() {
		return Optional.ofNullable(currentPage);
	}

	public Optional<Boolean> getBookmarked() {
		return Optional.ofNullable(bookmarked);
	}

	public boolean isBookmarked() {
		return getBookmarked().orElse(false);
	}

	public Optional<String> getParent() {
		return Optional.ofNullable(parent);
	}

}
