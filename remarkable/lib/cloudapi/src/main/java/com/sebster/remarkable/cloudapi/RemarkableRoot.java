package com.sebster.remarkable.cloudapi;

import static java.util.Collections.unmodifiableList;
import static lombok.AccessLevel.PACKAGE;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = PACKAGE)
public class RemarkableRoot implements RemarkableCollection {

	private final List<RemarkableFolder> folders = new ArrayList<>();
	private final List<RemarkableDocument> documents = new ArrayList<>();

	public List<RemarkableFolder> getFolders() {
		return unmodifiableList(folders);
	}

	public List<RemarkableDocument> getDocuments() {
		return unmodifiableList(documents);
	}

	@Override
	public String toString() {
		return "[Root]";
	}

	void addDocument(@NonNull RemarkableDocument document) {
		documents.add(document);
	}

	void addFolder(@NonNull RemarkableFolder folder) {
		folders.add(folder);
	}

	public static RemarkableRootBuilder builder() {
		return new RemarkableRootBuilder();
	}

}
