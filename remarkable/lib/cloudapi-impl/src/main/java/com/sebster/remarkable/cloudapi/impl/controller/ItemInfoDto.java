package com.sebster.remarkable.cloudapi.impl.controller;

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

	@NonNull UUID id;
	int version;

	String type;
	String name;
	UUID parentId;
	URI downloadUrl;
	Instant downloadUrlExpiration;
	Instant modificationTime;
	Integer currentPage;
	Boolean bookmarked;

	String errorMessage;

	public Optional<String> getType() {
		return Optional.ofNullable(type);
	}

	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}

	public Optional<UUID> getParentId() {
		return Optional.ofNullable(parentId);
	}

	public Optional<URI> getDownloadUrl() {
		return Optional.ofNullable(downloadUrl);
	}

	public Optional<Instant> getDownloadUrlExpiration() {
		return Optional.ofNullable(downloadUrlExpiration);
	}

	public Optional<RemarkableDownloadLink> getDownloadLink() {
		if (downloadUrl == null) {
			return Optional.empty();
		}
		return Optional.of(new RemarkableDownloadLink(id, downloadUrl, downloadUrlExpiration));
	}

	public Optional<Instant> getModificationTime() {
		return Optional.ofNullable(modificationTime);
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

	public boolean hasError() {
		return errorMessage != null;
	}

	public Optional<String> getErrorMessage() {
		return Optional.ofNullable(errorMessage);
	}

}
