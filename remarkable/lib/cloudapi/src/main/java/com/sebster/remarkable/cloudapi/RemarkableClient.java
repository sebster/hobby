package com.sebster.remarkable.cloudapi;

import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

import lombok.NonNull;

public interface RemarkableClient {

	UUID getId();

	String getDescription();

	RemarkableRootFolder list();

	InputStream download(@NonNull RemarkableItem item);

	default void createFolders(@NonNull Collection<RemarkablePath> paths) {
		createFolders(null, paths);
	}

	void createFolders(RemarkableFolder parent, @NonNull Collection<RemarkablePath> paths);

	void delete(@NonNull Collection<? extends RemarkableItem> items, boolean recursive);

	void clearCaches();

}