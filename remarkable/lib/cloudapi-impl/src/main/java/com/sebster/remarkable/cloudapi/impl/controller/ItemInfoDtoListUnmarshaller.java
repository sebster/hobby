package com.sebster.remarkable.cloudapi.impl.controller;

import static com.sebster.commons.functions.Functions.unwrapOptional;
import static com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto.DOCUMENT_TYPE;
import static com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto.FOLDER_TYPE;
import static java.time.Instant.EPOCH;
import static java.util.Comparator.comparing;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableCollectionBuilder;
import com.sebster.remarkable.cloudapi.RemarkableDocument;
import com.sebster.remarkable.cloudapi.RemarkableFolder;
import com.sebster.remarkable.cloudapi.RemarkableRoot;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
class ItemInfoDtoListUnmarshaller {

	private final @NonNull RemarkableClient client;
	private final @NonNull List<ItemInfoDto> itemInfos;

	public RemarkableRoot unmarshal() {
		RemarkableCollectionBuilder<RemarkableRoot> rootBuilder = RemarkableRoot.builder(client);
		unmarshalIntoCollection(rootBuilder);
		return rootBuilder.build();
	}

	private void unmarshalIntoCollection(RemarkableCollectionBuilder<?> parent) {
		unmarshalFolders(parent).forEach(parent::addFolder);
		unmarshalDocuments(parent).forEach(parent::addDocument);
	}

	private Stream<RemarkableFolder> unmarshalFolders(RemarkableCollectionBuilder<?> parent) {
		return getChildren(parent, FOLDER_TYPE).map(itemInfo -> unmarshalFolder(parent, itemInfo));
	}

	private RemarkableFolder unmarshalFolder(RemarkableCollectionBuilder<?> parent, ItemInfoDto itemInfo) {
		RemarkableCollectionBuilder<RemarkableFolder> folderBuilder = RemarkableFolder.builder(
				itemInfo.getId(),
				itemInfo.getVersion(),
				parent.getCollection(),
				itemInfo.getName().orElse(itemInfo.getId().toString()),
				itemInfo.getModificationTime().orElse(EPOCH),
				itemInfo.getDownloadLink().orElse(null)
		);
		unmarshalIntoCollection(folderBuilder);
		return folderBuilder.build();
	}

	private Stream<RemarkableDocument> unmarshalDocuments(RemarkableCollectionBuilder<?> parent) {
		return getChildren(parent, DOCUMENT_TYPE).map(itemInfo -> unmarshalDocument(parent, itemInfo));
	}

	private RemarkableDocument unmarshalDocument(RemarkableCollectionBuilder<?> parent, ItemInfoDto itemInfo) {
		return new RemarkableDocument(
				itemInfo.getId(),
				itemInfo.getVersion(),
				parent.getCollection(),
				itemInfo.getName().orElse(itemInfo.getId().toString()),
				itemInfo.getModificationTime().orElse(EPOCH),
				itemInfo.getDownloadLink().orElse(null),
				itemInfo.getCurrentPage().orElse(0),
				itemInfo.isBookmarked()
		);
	}

	private Stream<ItemInfoDto> getChildren(RemarkableCollectionBuilder<?> parent, String type) {
		UUID parentId = parent.getId().orElse(null);
		return itemInfos.stream()
				.filter(itemInfo -> Objects.equals(parentId, itemInfo.getParentId().orElse(null)))
				.filter(itemInfo -> Objects.equals(type, itemInfo.getType().orElse(null)))
				.sorted(comparing(unwrapOptional(ItemInfoDto::getName)));
	}

}
