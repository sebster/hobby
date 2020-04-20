package com.sebster.remarkable.cloudapi;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PACKAGE;

import java.util.Optional;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = PACKAGE)
public class RemarkableRootFolderBuilder implements RemarkableCollectionBuilder {

	private RemarkableRootFolder rootFolder = new RemarkableRootFolder();

	@Override
	public Optional<RemarkableFolder> getFolder() {
		return Optional.empty();
	}

	@Override
	public void addFolder(@NonNull RemarkableFolder folder) {
		rootFolder.addFolder(folder);
	}

	@Override
	public void addDocument(@NonNull RemarkableDocument document) {
		rootFolder.addDocument(document);
	}

	@Override
	public RemarkableRootFolder build() {
		requireNonNull(rootFolder);
		RemarkableRootFolder result = rootFolder;
		rootFolder = null;
		return result;
	}

}
