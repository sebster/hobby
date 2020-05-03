package com.sebster.remarkable.cloudapi;

import static com.sebster.remarkable.cloudapi.RemarkableItemType.DOCUMENT;
import static com.sebster.remarkable.cloudapi.RemarkableItemType.FOLDER;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * A reMarkable item is either a folder or a document.
 */
@EqualsAndHashCode(of = "id")
@Getter
public abstract class RemarkableItem {

	private final @NonNull UUID id;
	private final int version;
	private final @NonNull RemarkableItemType type;
	private final @NonNull RemarkableCollection parent;
	private final @NonNull String name;
	private final @NonNull Instant modificationTime;
	private final RemarkableDownloadLink downloadLink;

	public RemarkableItem(
			@NonNull UUID id,
			int version,
			@NonNull RemarkableItemType type,
			@NonNull RemarkableCollection parent,
			@NonNull String name,
			@NonNull Instant modificationTime,
			RemarkableDownloadLink downloadLink
	) {
		this.id = id;
		this.version = version;
		this.type = type;
		this.parent = parent;
		this.name = name;
		this.modificationTime = modificationTime;
		this.downloadLink = downloadLink;
	}

	public RemarkableCollection getParent() {
		return parent;
	}

	public RemarkablePath getParentPath() {
		return parent.getPath();
	}

	public Optional<RemarkableFolder> getParentFolder() {
		return parent.isFolder() ? Optional.of(parent.asFolder()) : Optional.empty();
	}

	public boolean hasType(@NonNull RemarkableItemType type) {
		return getType() == type;
	}

	public boolean isFolder() {
		return hasType(FOLDER);
	}

	public RemarkableFolder asFolder() {
		throw new UnsupportedOperationException("Not a folder: " + this);
	}

	public boolean isDocument() {
		return hasType(DOCUMENT);
	}

	public RemarkableDocument asDocument() {
		throw new UnsupportedOperationException("Not a document: " + this);
	}

	public RemarkablePath getPath() {
		return parent.getPath().append(name);
	}

	public Optional<RemarkableDownloadLink> getDownloadLink() {
		return Optional.ofNullable(downloadLink);
	}

	public void doWithItem(
			@NonNull Consumer<RemarkableFolder> folderAction,
			@NonNull Consumer<RemarkableDocument> documentAction
	) {
		switch (type) {
		case FOLDER:
			folderAction.accept(this.asFolder());
			break;
		case DOCUMENT:
			documentAction.accept(this.asDocument());
			break;
		}
	}

	@Override
	public String toString() {
		return getPath().toString();
	}

}
