package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ItemInfoDtoJsonMapping {

	@JsonCreator
	public ItemInfoDtoJsonMapping(
			@JsonProperty("ID") String id,
			@JsonProperty("Version") int version,
			@JsonProperty("Message") String message,
			@JsonProperty("Success") boolean success,
			@JsonProperty("BlobURLGet") String blobUrlGet,
			@JsonProperty("BlobURLGetExpires") String blobUrlGetExpires,
			@JsonProperty("ModifiedClient") String modifiedClient,
			@JsonProperty("Type") String type,
			@JsonProperty("VissibleName") String visibleName,
			@JsonProperty("CurrentPage") int currentPage,
			@JsonProperty("Bookmarked") boolean bookmarked,
			@JsonProperty("Parent") String parent
	) {
	}

}
