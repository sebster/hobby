package com.sebster.remarkable.cloudapi;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.NonNull;

public interface RemarkableCollection extends Iterable<RemarkableItem> {

	List<RemarkableFolder> getFolders();

	List<RemarkableDocument> getDocuments();

	default Optional<RemarkableItem> findItem(@NonNull String name) {
		return stream().filter(item -> Objects.equals(name, item.getName())).findFirst();
	}

	default Optional<RemarkableFolder> findFolder(@NonNull String name) {
		return getFolders().stream().filter(folder -> Objects.equals(folder.getName(), name)).findFirst();
	}

	default Optional<RemarkableDocument> findDocument(@NonNull String name) {
		return getDocuments().stream().filter(document -> Objects.equals(document.getName(), name)).findFirst();
	}

	default Optional<RemarkableItem> findItem(@NonNull RemarkablePath path) {
		if (path.getTail().isEmpty()) {
			return findItem(path.getHead());
		} else {
			return findFolder(path.getHead()).flatMap(folder -> folder.findItem(path.getTail().get()));
		}
	}

	default Optional<RemarkableFolder> findFolder(@NonNull RemarkablePath path) {
		return findItem(path).filter(RemarkableItem::isFolder).map(RemarkableItem::asFolder);
	}

	default Optional<RemarkableDocument> findDocument(@NonNull RemarkablePath path) {
		return findItem(path).filter(RemarkableItem::isDocument).map(RemarkableItem::asDocument);
	}

	default RemarkableItem getItem(@NonNull RemarkablePath path) {
		return findItem(path).orElseThrow(() -> new IllegalArgumentException("Not found: " + path));
	}

	default RemarkableFolder getFolder(@NonNull RemarkablePath path) {
		return findFolder(path).orElseThrow(() -> new IllegalArgumentException("Not found: " + path));
	}

	default RemarkableDocument getDocument(@NonNull RemarkablePath path) {
		return findDocument(path).orElseThrow(() -> new IllegalArgumentException("Not found: " + path));
	}

	default boolean hasItem(@NonNull RemarkablePath path) {
		return findItem(path).isPresent();
	}

	default boolean hasFolder(@NonNull RemarkablePath path) {
		return findFolder(path).isPresent();
	}

	default boolean hasDocument(@NonNull RemarkablePath path) {
		return findDocument(path).isPresent();
	}

	default Optional<RemarkableItem> findItem(@NonNull UUID id) {
		return recurse().filter(item -> Objects.equals(item.getId(), id)).findFirst();
	}

	default Optional<RemarkableFolder> findFolder(@NonNull UUID id) {
		return findItem(id).filter(RemarkableItem::isFolder).map(RemarkableItem::asFolder);
	}

	default Optional<RemarkableDocument> findDocument(@NonNull UUID id) {
		return findItem(id).filter(RemarkableItem::isDocument).map(RemarkableItem::asDocument);
	}

	default RemarkableItem getItem(@NonNull UUID id) {
		return findItem(id).orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
	}

	default RemarkableFolder getFolder(@NonNull UUID id) {
		return findFolder(id).orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
	}

	default RemarkableDocument getDocument(@NonNull UUID id) {
		return findDocument(id).orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
	}

	default boolean hasItem(@NonNull UUID id) {
		return findItem(id).isPresent();
	}

	default boolean hasFolder(@NonNull UUID id) {
		return findFolder(id).isPresent();
	}

	default boolean hasDocument(@NonNull UUID id) {
		return findDocument(id).isPresent();
	}

	default Stream<RemarkableItem> stream() {
		return Stream.concat(getFolders().stream(), getDocuments().stream());
	}

	@Override
	default Iterator<RemarkableItem> iterator() {
		return stream().iterator();
	}

	default Stream<RemarkableItem> recurse() {
		return Stream.concat(
				getFolders().stream().flatMap(folder -> Stream.concat(Stream.of(folder), folder.recurse())),
				getDocuments().stream()
		);
	}

}
