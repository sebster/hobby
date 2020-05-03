package com.sebster.remarkable.cloudapi;

import static com.sebster.remarkable.cloudapi.RemarkablePath.empty;
import static java.util.Collections.unmodifiableList;
import static lombok.AccessLevel.PACKAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = PACKAGE)
public class RemarkableRoot implements RemarkableCollection {

	private final List<RemarkableFolder> folders = new ArrayList<>();
	private final List<RemarkableDocument> documents = new ArrayList<>();

	@Override
	public RemarkableCollection getParent() {
		return this;
	}

	@Override
	public Optional<RemarkableFolder> getParentFolder() {
		return Optional.empty();
	}

	@Override
	public RemarkablePath getPath() {
		return empty();
	}

	@Override
	public RemarkablePath getParentPath() {
		return empty();
	}

	@Override
	public boolean isRoot() {
		return true;
	}

	@Override
	public RemarkableRoot asRoot() {
		return this;
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
	public String toString() {
		return "[Root]";
	}

	public static RemarkableCollectionBuilder<RemarkableRoot> builder() {
		RemarkableRoot underConstruction = new RemarkableRoot();
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

}
