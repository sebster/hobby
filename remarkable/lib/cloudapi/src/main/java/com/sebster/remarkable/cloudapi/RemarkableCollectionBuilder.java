package com.sebster.remarkable.cloudapi;

import java.util.Optional;

import lombok.NonNull;

public interface RemarkableCollectionBuilder {

	Optional<RemarkableFolder> getFolder();

	void addFolder(@NonNull RemarkableFolder folder);

	void addDocument(@NonNull RemarkableDocument document);

	RemarkableCollection build();
}
