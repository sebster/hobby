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
			RemarkableFolder parent,
			String name,
			Instant modificationTime,
			RemarkableDownloadLink downloadLink
	) {
		super(id, version, FOLDER, parent, name, modificationTime, downloadLink);
	}

	public List<RemarkableFolder> getFolders() {
		return unmodifiableList(folders);
	}

	public List<RemarkableDocument> getDocuments() {
		return unmodifiableList(documents);
	}

	public RemarkableFolder asFolder() {
		return this;
	}

	@Override
	public Stream<RemarkableItem> recurse() {
		return Stream.concat(Stream.of(this), RemarkableCollection.super.recurse());
	}

	void addDocument(@NonNull RemarkableDocument document) {
		documents.add(document);
	}

	void addFolder(@NonNull RemarkableFolder folder) {
		folders.add(folder);
	}

	public static RemarkableFolderBuilder builder(
			@NonNull UUID id,
			int version,
			RemarkableFolder parent,
			@NonNull String name,
			@NonNull Instant modificationTime,
			RemarkableDownloadLink downloadLink
	) {
		return new RemarkableFolderBuilder(id, version, parent, name, modificationTime, downloadLink);
	}

	@Override
	public String toString() {
		return super.toString() + "/";
	}

}
