package com.sebster.remarkable.cloudapi;

import static java.util.Collections.singleton;

import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

import lombok.NonNull;

public interface RemarkableClient {

	UUID getId();

	String getDescription();

	RemarkableRootFolder list();

	InputStream download(@NonNull RemarkableItem item);

	void createFolders(@NonNull RemarkableCollection parent, @NonNull Collection<RemarkablePath> paths);

	default void createFolders(@NonNull Collection<RemarkablePath> paths) {
		createFolders(list(), paths);
	}

	default void createFolder(@NonNull RemarkableCollection parent, @NonNull RemarkablePath path) {
		createFolders(parent, singleton(path));
	}

	default void createFolder(@NonNull RemarkablePath path) {
		createFolders(singleton(path));
	}

	void delete(@NonNull Collection<? extends RemarkableItem> items, boolean recursive);

	void clearCaches();

}