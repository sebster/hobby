package com.sebster.remarkable.cloudapi;

import static com.sebster.remarkable.cloudapi.RemarkablePath.empty;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NonNull;

public class RemarkableRoot implements RemarkableCollection {

	@Getter
	private final RemarkableClient client;

	private final List<RemarkableFolder> folders = new ArrayList<>();
	private final List<RemarkableDocument> documents = new ArrayList<>();

	private RemarkableRoot(@NonNull RemarkableClient client) {
		this.client = client;
	}

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

	public static RemarkableCollectionBuilder<RemarkableRoot> builder(@NonNull RemarkableClient client) {
		return new RemarkableRootBuilder(new RemarkableRoot(client));
	}

	public static class RemarkableRootBuilder extends RemarkableCollectionBuilder<RemarkableRoot> {

		public RemarkableRootBuilder(RemarkableRoot underConstruction) {
			super(underConstruction);
		}

		@Override
		public void addFolder(@NonNull RemarkableFolder folder) {
			getCollection().folders.add(folder);
		}

		@Override
		public void addDocument(@NonNull RemarkableDocument document) {
			getCollection().documents.add(document);
		}

	}

}
