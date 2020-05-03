package com.sebster.remarkable.cloudapi;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

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

	default List<RemarkableItem> getItems() {
		return stream().collect(toList());
	}

	default List<RemarkableItem> getItems(RemarkableItemType type) {
		return stream(type).collect(toList());
	}

	default boolean isEmpty() {
		return getFolders().isEmpty() && getDocuments().isEmpty();
	}

	default boolean isNotEmpty() {
		return !isEmpty();
	}

	RemarkableCollection getParent();

	Optional<RemarkableFolder> getParentFolder();

	default boolean isRoot() {
		return false;
	}

	default RemarkableRoot asRoot() {
		throw new UnsupportedOperationException("Not the root: " + this);
	}

	default boolean isFolder() {
		return false;
	}

	default RemarkableFolder asFolder() {
		throw new UnsupportedOperationException("Not a folder: " + this);
	}

	RemarkablePath getPath();

	RemarkablePath getParentPath();

	default Optional<RemarkableItem> findItem(@NonNull String name) {
		return stream().filter(item -> Objects.equals(name, item.getName())).findFirst();
	}

	default Optional<RemarkableFolder> findFolder(@NonNull String name) {
		return getFolders().stream().filter(folder -> Objects.equals(folder.getName(), name)).findFirst();
	}

	default Optional<RemarkableDocument> findDocument(@NonNull String name) {
		return getDocuments().stream().filter(document -> Objects.equals(document.getName(), name)).findFirst();
	}

	default RemarkableItem getItem(@NonNull String name) {
		return findItem(name).orElseThrow(() -> new IllegalArgumentException("Not found: " + name));
	}

	default RemarkableFolder getFolder(@NonNull String name) {
		return findFolder(name).orElseThrow(() -> new IllegalArgumentException("Not found: " + name));
	}

	default RemarkableDocument getDocument(@NonNull String name) {
		return findDocument(name).orElseThrow(() -> new IllegalArgumentException("Not found: " + name));
	}

	default boolean hasItem(@NonNull String name) {
		return findItem(name).isPresent();
	}

	default boolean hasFolder(@NonNull String name) {
		return findFolder(name).isPresent();
	}

	default boolean hasDocument(@NonNull String name) {
		return findDocument(name).isPresent();
	}

	default Optional<RemarkableItem> findItem(@NonNull RemarkablePath path) {
		if (path.isEmpty()) {
			return this.isFolder() ? Optional.of(this.asFolder()) : Optional.empty();
		}
		String first = path.getHead();
		RemarkablePath rest = path.getTail();
		if (".".equals(first)) {
			return findItem(rest);
		}
		if ("..".equals(first)) {
			return getParent().findItem(rest);
		}
		Optional<RemarkableItem> item = findItem(first);
		if (item.isEmpty()) {
			return item;
		}
		if (rest.isEmpty()) {
			return item;
		}
		if (item.get().isDocument()) {
			return Optional.empty();
		}
		return item.get().asFolder().findItem(rest);
	}

	default Optional<RemarkableCollection> findCollection(@NonNull RemarkablePath path) {
		return path.isEmpty() ? Optional.of(this) : findFolder(path).map(identity());
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

	default Stream<RemarkableItem> stream(RemarkableItemType type) {
		return type != null ? stream().filter(item -> item.hasType(type)) : stream();
	}

	@Override
	default Iterator<RemarkableItem> iterator() {
		return stream().iterator();
	}

	default Stream<RemarkableItem> recurse() {
		return Stream.concat(
				getFolders().stream().flatMap(RemarkableFolder::recurse),
				getDocuments().stream()
		);
	}

	default Stream<RemarkableItem> recurse(RemarkableItemType itemType) {
		return itemType != null ? recurse().filter(item -> item.hasType(itemType)) : recurse();
	}

}
