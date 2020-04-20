package com.sebster.remarkable.cloudapi.impl.controller;

import static java.util.Comparator.comparing;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import com.sebster.remarkable.cloudapi.RemarkableCollectionBuilder;
import com.sebster.remarkable.cloudapi.RemarkableDocument;
import com.sebster.remarkable.cloudapi.RemarkableFolder;
import com.sebster.remarkable.cloudapi.RemarkableFolderBuilder;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkableRootFolder;
import com.sebster.remarkable.cloudapi.RemarkableRootFolderBuilder;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
class ItemInfoDtoListUnmarshaller {

	public static final String FOLDER_TYPE = "CollectionType";
	public static final String DOCUMENT_TYPE = "DocumentType";

	private final @NonNull List<ItemInfoDto> itemInfos;

	public RemarkableRootFolder unmarshal() {
		RemarkableRootFolderBuilder rootFolderBuilder = RemarkableRootFolder.builder();
		unmarshalIntoCollection(rootFolderBuilder);
		return rootFolderBuilder.build();
	}

	private void unmarshalIntoCollection(RemarkableCollectionBuilder parent) {
		unmarshalFolders(parent).forEach(parent::addFolder);
		unmarshalDocuments(parent).forEach(parent::addDocument);
	}

	private Stream<RemarkableFolder> unmarshalFolders(RemarkableCollectionBuilder parent) {
		return getChildren(parent, FOLDER_TYPE).map(itemInfo -> unmarshalFolder(parent, itemInfo));
	}

	private RemarkableFolder unmarshalFolder(RemarkableCollectionBuilder parent, ItemInfoDto itemInfo) {
		RemarkableFolderBuilder folderBuilder = RemarkableFolder.builder(
				UUID.fromString(itemInfo.getId()),
				itemInfo.getVersion(),
				parent.getFolder().orElse(null),
				itemInfo.getVisibleName(),
				Instant.parse(itemInfo.getModifiedClient()),
				itemInfo.getDownloadLink().orElse(null)
		);
		unmarshalIntoCollection(folderBuilder);
		return folderBuilder.build();
	}

	private Stream<RemarkableDocument> unmarshalDocuments(RemarkableCollectionBuilder parent) {
		return getChildren(parent, DOCUMENT_TYPE).map(itemInfo -> unmarshalDocument(parent, itemInfo));
	}

	private RemarkableDocument unmarshalDocument(RemarkableCollectionBuilder parent, ItemInfoDto itemInfo) {
		return new RemarkableDocument(
				UUID.fromString(itemInfo.getId()),
				itemInfo.getVersion(),
				parent.getFolder().orElse(null),
				itemInfo.getVisibleName(),
				Instant.parse(itemInfo.getModifiedClient()),
				itemInfo.getDownloadLink().orElse(null),
				itemInfo.getCurrentPage(),
				itemInfo.isBookmarked()
		);
	}

	private Stream<ItemInfoDto> getChildren(RemarkableCollectionBuilder parent, String type) {
		String parentId = parent.getFolder().map(RemarkableItem::getId).map(UUID::toString).orElse("");
		return itemInfos.stream()
				.filter(itemInfo -> Objects.equals(itemInfo.getParent(), parentId))
				.filter(itemInfo -> Objects.equals(itemInfo.getType(), type))
				.sorted(comparing(ItemInfoDto::getVisibleName));
	}

}
