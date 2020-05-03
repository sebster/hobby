package com.sebster.remarkable.cloudapi.impl.controller;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ItemInfoDto implements ErrorDto<ItemInfoDto> {

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

	@Override
	public Optional<String> getErrorMessage() {
		return Optional.ofNullable(errorMessage);
	}

	public static ItemInfoDto fromItem(@NonNull RemarkableItem item) {
		ItemInfoDtoBuilder builder = ItemInfoDto.builder()
				.id(item.getId())
				.version(item.getVersion())
				.type(item.isFolder() ? FOLDER_TYPE : DOCUMENT_TYPE)
				.name(item.getName())
				.parentId(item.getParentFolder().map(RemarkableItem::getId).orElse(null))
				.modificationTime(item.getModificationTime());

		if (item.isDocument()) {
			builder.bookmarked(item.asDocument().isBookmarked());
			builder.currentPage(item.asDocument().getCurrentPage());
		}

		return builder.build();
	}

}
