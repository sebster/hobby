package com.sebster.remarkable.cloudapi;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import lombok.NonNull;

public class RemarkableFolderBuilder implements RemarkableCollectionBuilder {

	private RemarkableFolder folder;

	RemarkableFolderBuilder(
			UUID id,
			int version,
			RemarkableFolder parent,
			String name,
			Instant modificationTime,
			RemarkableDownloadLink downloadLink
	) {
		folder = new RemarkableFolder(id, version, parent, name, modificationTime, downloadLink);
	}

	@Override
	public Optional<RemarkableFolder> getFolder() {
		return Optional.of(folder);
	}

	@Override
	public void addFolder(@NonNull RemarkableFolder folder) {
		this.folder.addFolder(folder);
	}

	@Override
	public void addDocument(@NonNull RemarkableDocument document) {
		folder.addDocument(document);
	}

	@Override
	public RemarkableFolder build() {
		requireNonNull(folder);
		RemarkableFolder result = folder;
		folder = null;
		return result;
	}

}
