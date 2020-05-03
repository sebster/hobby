package com.sebster.remarkable.cloudapi;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class RemarkableCollectionBuilder<T extends RemarkableCollection> {

	private T collection;

	public RemarkableCollection getCollection() {
		return collection;
	}

	public Optional<UUID> getId() {
		return collection.isFolder() ? Optional.ofNullable(collection.asFolder().getId()) : Optional.empty();
	}

	public abstract void addFolder(@NonNull RemarkableFolder folder);

	public abstract void addDocument(@NonNull RemarkableDocument document);

	public T build() {
		requireNonNull(collection, "Collection was already built");
		T result = collection;
		collection = null;
		return result;
	}

}
