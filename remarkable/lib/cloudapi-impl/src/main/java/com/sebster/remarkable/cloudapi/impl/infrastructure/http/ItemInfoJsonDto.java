package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.lang.Boolean.FALSE;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.commons.strings.Strings;
import com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@JsonInclude(NON_NULL)
public class ItemInfoJsonDto {

	@JsonProperty("ID")
	private String id;
	@JsonProperty("Version")
	private int version;

	@JsonProperty("Message")
	private String message;
	@JsonProperty("Success")
	private Boolean success;
	@JsonProperty("BlobURLGet")
	private String blobUrlGet;
	@JsonProperty("BlobURLGetExpires")
	private String blobUrlGetExpires;
	@JsonProperty("ModifiedClient")
	private String modifiedClient;
	@JsonProperty("Type")
	private String type;
	@JsonProperty("VissibleName")
	private String visibleName;
	@JsonProperty("CurrentPage")
	private Integer currentPage;
	@JsonProperty("Bookmarked")
	private Boolean bookmarked;
	@JsonProperty("Parent")
	private String parent;

	ItemInfoDto unmarshal() {
		var builder = ItemInfoDto.builder().
				id(UUID.fromString(id))
				.version(version)
				.type(type)
				.name(visibleName)
				.parentId(Optional.ofNullable(parent).filter(Strings::isNotBlank).map(UUID::fromString).orElse(null))
				.downloadUrl(Optional.ofNullable(blobUrlGet).map(URI::create).orElse(null))
				.downloadUrlExpiration(Optional.ofNullable(blobUrlGetExpires).map(Instant::parse).orElse(null))
				.modificationTime(Optional.ofNullable(modifiedClient).map(Instant::parse).orElse(null))
				.currentPage(currentPage)
				.bookmarked(bookmarked);

		if (FALSE.equals(success)) {
			builder.errorMessage(Optional.ofNullable(message).filter(Strings::isNotBlank).orElse("Unknown error"));
		}

		return builder.build();
	}

	static ItemInfoJsonDto marshal(@NonNull ItemInfoDto itemInfo) {
		return ItemInfoJsonDto.builder()
				.id(itemInfo.getId().toString())
				.version(itemInfo.getVersion())
				.message(itemInfo.getErrorMessage().orElse(null))
				.success(itemInfo.hasError())
				.blobUrlGet(itemInfo.getDownloadUrl().map(Object::toString).orElse(null))
				.blobUrlGetExpires(itemInfo.getDownloadUrlExpiration().map(Object::toString).orElse(null))
				.modifiedClient(itemInfo.getModificationTime().map(Object::toString).orElse(null))
				.type(itemInfo.getType().orElse(null))
				.visibleName(itemInfo.getName().orElse(null))
				.currentPage(itemInfo.getCurrentPage().orElse(null))
				.bookmarked(itemInfo.getBookmarked().orElse(null))
				.parent(itemInfo.getParentId().map(Object::toString).orElse(null))
				.build();
	}

	static ItemInfoJsonDto marshalIdAndVersionOnly(@NonNull ItemInfoDto itemInfo) {
		return ItemInfoJsonDto.builder().id(itemInfo.getId().toString()).version(itemInfo.getVersion()).build();
	}

}
