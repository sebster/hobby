package com.sebster.remarkable.cloudapi;

import static com.sebster.remarkable.cloudapi.RemarkableItemType.DOCUMENT;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class RemarkableDocument extends RemarkableItem {

	private final int currentPage;
	private final boolean bookmarked;

	public RemarkableDocument(
			@NonNull UUID id,
			int version,
			@NonNull RemarkableCollection parent,
			@NonNull String name,
			@NonNull Instant modificationTime,
			RemarkableDownloadLink downloadLink,
			int currentPage,
			boolean bookmarked
	) {
		super(id, version, DOCUMENT, parent, name, modificationTime, downloadLink);
		this.currentPage = currentPage;
		this.bookmarked = bookmarked;
	}

	@Override
	public RemarkableDocument asDocument() {
		return this;
	}

}
