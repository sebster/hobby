package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.lang.Boolean.FALSE;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto;
import lombok.Builder;
import lombok.Data;

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
				.parentId(Optional.ofNullable(parent).filter(StringUtils::isNotBlank).map(UUID::fromString).orElse(null))
				.downloadLink(URI.create(blobUrlGet))
				.downloadLinkExpiration(Optional.ofNullable(blobUrlGetExpires).map(Instant::parse).orElse(null))
				.modificationTime(Optional.ofNullable(modifiedClient).map(Instant::parse).orElse(null))
				.currentPage(currentPage)
				.bookmarked(bookmarked);

		if (FALSE.equals(success)) {
			builder.errorMessage(Optional.ofNullable(message).filter(StringUtils::isNotBlank).orElse("Unknown error"));
		}

		return builder.build();
	}

	static ItemInfoJsonDto marshal(ItemInfoDto itemInfoDto) {
		return ItemInfoJsonDto.builder()
				.id(itemInfoDto.getId().toString())
				.version(itemInfoDto.getVersion())
				.message(itemInfoDto.getErrorMessage().orElse(null))
				.success(itemInfoDto.hasError())
				.blobUrlGet(itemInfoDto.getDownloadLink().map(Object::toString).orElse(null))
				.blobUrlGetExpires(itemInfoDto.getDownloadLinkExpiration().map(Object::toString).orElse(null))
				.modifiedClient(itemInfoDto.getModificationTime().map(Object::toString).orElse(null))
				.type(itemInfoDto.getType().orElse(null))
				.visibleName(itemInfoDto.getName().orElse(null))
				.currentPage(itemInfoDto.getCurrentPage().orElse(null))
				.bookmarked(itemInfoDto.getBookmarked().orElse(null))
				.parent(itemInfoDto.getParentId().map(Object::toString).orElse(null))
				.build();
	}

}
