package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
		return new ItemInfoDto(
				id,
				version,
				message,
				success,
				blobUrlGet,
				blobUrlGetExpires,
				modifiedClient,
				type,
				visibleName,
				currentPage,
				bookmarked,
				parent
		);
	}

	static ItemInfoJsonDto marshal(ItemInfoDto itemInfoDto) {
		return new ItemInfoJsonDto(
				itemInfoDto.getId(),
				itemInfoDto.getVersion(),
				itemInfoDto.getMessage().orElse(null),
				itemInfoDto.getSuccess().orElse(null),
				itemInfoDto.getBlobUrlGet().orElse(null),
				itemInfoDto.getBlobUrlGetExpires().orElse(null),
				itemInfoDto.getModifiedClient().orElse(null),
				itemInfoDto.getType().orElse(null),
				itemInfoDto.getVisibleName().orElse(null),
				itemInfoDto.getCurrentPage().orElse(null),
				itemInfoDto.getBookmarked().orElse(null),
				itemInfoDto.getParent().orElse(null)
		);
	}

}
