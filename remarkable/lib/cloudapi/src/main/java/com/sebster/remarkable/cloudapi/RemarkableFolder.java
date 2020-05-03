package com.sebster.remarkable.cloudapi;

import static com.sebster.remarkable.cloudapi.RemarkableItemType.FOLDER;
import static java.util.Collections.unmodifiableList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.NonNull;

public final class RemarkableFolder extends RemarkableItem implements RemarkableCollection {

	private final List<RemarkableFolder> folders = new ArrayList<>();
	private final List<RemarkableDocument> documents = new ArrayList<>();

	RemarkableFolder(
			UUID id,
			int version,
			RemarkableCollection parent,
			String name,
			Instant modificationTime,
			RemarkableDownloadLink downloadLink
	) {
		super(id, version, FOLDER, parent, name, modificationTime, downloadLink);
	}

	@Override
	public List<RemarkableFolder> getFolders() {
		return unmodifiableList(folders);
	}

	@Override
	public List<RemarkableDocument> getDocuments() {
		return unmodifiableList(documents);
	}

	@Override
	public RemarkableFolder asFolder() {
		return this;
	}

	@Override
	public Stream<RemarkableItem> recurse() {
		return Stream.concat(Stream.of(this), RemarkableCollection.super.recurse());
	}

	public static RemarkableCollectionBuilder<RemarkableFolder> builder(
			@NonNull UUID id,
			int version,
			@NonNull RemarkableCollection parent,
			@NonNull String name,
			@NonNull Instant modificationTime,
			RemarkableDownloadLink downloadLink
	) {
		RemarkableFolder underConstruction = new RemarkableFolder(id, version, parent, name, modificationTime, downloadLink);
		return new RemarkableCollectionBuilder<>(underConstruction) {
			@Override
			public void addFolder(@NonNull RemarkableFolder folder) {
				underConstruction.folders.add(folder);
			}

			@Override
			public void addDocument(@NonNull RemarkableDocument document) {
				underConstruction.documents.add(document);
			}
		};
	}

	@Override
	public String toString() {
		return super.toString() + "/";
	}

}
