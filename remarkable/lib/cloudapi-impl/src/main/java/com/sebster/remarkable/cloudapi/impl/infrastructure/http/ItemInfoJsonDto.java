package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
		return ItemInfoDto.builder()
				.id(id)
				.version(version)
				.message(message)
				.success(success)
				.blobUrlGet(blobUrlGet)
				.blobUrlGetExpires(blobUrlGetExpires)
				.modifiedClient(modifiedClient)
				.type(type)
				.visibleName(visibleName)
				.currentPage(currentPage)
				.bookmarked(bookmarked)
				.parent(parent)
				.build();
	}

	static ItemInfoJsonDto marshal(ItemInfoDto itemInfoDto) {
		return ItemInfoJsonDto.builder()
				.id(itemInfoDto.getId())
				.version(itemInfoDto.getVersion())
				.message(itemInfoDto.getMessage().orElse(null))
				.success(itemInfoDto.getSuccess().orElse(null))
				.blobUrlGet(itemInfoDto.getBlobUrlGet().orElse(null))
				.blobUrlGetExpires(itemInfoDto.getBlobUrlGetExpires().orElse(null))
				.modifiedClient(itemInfoDto.getModifiedClient().orElse(null))
				.type(itemInfoDto.getType().orElse(null))
				.visibleName(itemInfoDto.getVisibleName().orElse(null))
				.currentPage(itemInfoDto.getCurrentPage().orElse(null))
				.bookmarked(itemInfoDto.getBookmarked().orElse(null))
				.parent(itemInfoDto.getParent().orElse(null))
				.build();
	}

}
