package com.sebster.remarkable.cloudapi;

import static com.sebster.remarkable.cloudapi.RemarkableItemType.DOCUMENT;
import static com.sebster.remarkable.cloudapi.RemarkableItemType.FOLDER;
import static com.sebster.remarkable.cloudapi.RemarkablePath.path;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode(of = "id")
@Getter
public abstract class RemarkableItem {

	private final @NonNull UUID id;
	private final int version;
	private final @NonNull RemarkableItemType type;
	private final RemarkableFolder parent;
	private final @NonNull String name;
	private final @NonNull Instant modificationTime;
	private final RemarkableDownloadLink downloadLink;

	public RemarkableItem(
			@NonNull UUID id,
			int version,
			@NonNull RemarkableItemType type,
			RemarkableFolder parent,
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

	public Optional<RemarkableFolder> getParent() {
		return Optional.ofNullable(parent);
	}

	public boolean isFolder() {
		return getType() == FOLDER;
	}

	public RemarkableFolder asFolder() {
		throw new UnsupportedOperationException("Not a folder: " + this);
	}

	public boolean isDocument() {
		return getType() == DOCUMENT;
	}

	public RemarkableDocument asDocument() {
		throw new UnsupportedOperationException("Not a document: " + this);
	}

	public RemarkablePath getPath() {
		return parent != null ? path(parent.getPath(), name) : path(name);
	}

	public Optional<RemarkableDownloadLink> getDownloadLink() {
		return Optional.ofNullable(downloadLink);
	}

	public void doWithItem(
			@NonNull Consumer<RemarkableFolder> folderAction,
			@NonNull Consumer<RemarkableDocument> documentAction
	) {
		if (isFolder()) {
			folderAction.accept(this.asFolder());
		} else if (isDocument()) {
			documentAction.accept(this.asDocument());
		}
	}

	@Override
	public String toString() {
		return getPath().toString();
	}

}
