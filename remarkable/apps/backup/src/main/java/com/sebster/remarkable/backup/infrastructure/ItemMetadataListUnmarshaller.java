package com.sebster.remarkable.backup.infrastructure;

import static com.sebster.remarkable.backup.infrastructure.ItemMetadata.ItemType.DOCUMENT;
import static com.sebster.remarkable.backup.infrastructure.ItemMetadata.ItemType.FOLDER;
import static java.util.Comparator.comparing;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import com.sebster.remarkable.cloudapi.RemarkableCollectionBuilder;
import com.sebster.remarkable.cloudapi.RemarkableDocument;
import com.sebster.remarkable.cloudapi.RemarkableFolder;
import com.sebster.remarkable.cloudapi.RemarkableRoot;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
class ItemMetadataListUnmarshaller {

	private final @NonNull Collection<ItemMetadata> itemMetadata;

	RemarkableRoot unmarshal() {
		RemarkableCollectionBuilder<RemarkableRoot> rootBuilder = RemarkableRoot.builder();
		unmarshalIntoCollection(rootBuilder);
		return rootBuilder.build();
	}

	private void unmarshalIntoCollection(RemarkableCollectionBuilder<?> parent) {
		unmarshalFolders(parent).forEach(parent::addFolder);
		unmarshalDocuments(parent).forEach(parent::addDocument);
	}

	private Stream<RemarkableFolder> unmarshalFolders(RemarkableCollectionBuilder<?> parent) {
		return getChildren(parent, FOLDER).map(itemMetadata -> unmarshalFolder(parent, itemMetadata));
	}

	private RemarkableFolder unmarshalFolder(RemarkableCollectionBuilder<?> parent, ItemMetadata itemMetadata) {
		RemarkableCollectionBuilder<RemarkableFolder> folderBuilder = RemarkableFolder.builder(
				itemMetadata.getId(),
				itemMetadata.getVersion(),
				parent.getCollection(),
				itemMetadata.getName(),
				itemMetadata.getModificationTime(),
				null
		);
		unmarshalIntoCollection(folderBuilder);
		return folderBuilder.build();
	}

	private Stream<RemarkableDocument> unmarshalDocuments(RemarkableCollectionBuilder<?> parent) {
		return getChildren(parent, DOCUMENT).map(itemInfo -> unmarshalDocument(parent, itemInfo));
	}

	private RemarkableDocument unmarshalDocument(RemarkableCollectionBuilder<?> parent, ItemMetadata itemMetadata) {
		return new RemarkableDocument(
				itemMetadata.getId(),
				itemMetadata.getVersion(),
				parent.getCollection(),
				itemMetadata.getName(),
				itemMetadata.getModificationTime(),
				null,
				itemMetadata.getCurrentPage(),
				itemMetadata.isBookmarked()
		);
	}

	private Stream<ItemMetadata> getChildren(RemarkableCollectionBuilder<?> parent, ItemMetadata.ItemType type) {
		UUID parentId = parent.getId().orElse(null);
		return itemMetadata.stream()
				.filter(itemInfo -> Objects.equals(itemInfo.getParentId(), parentId))
				.filter(itemInfo -> itemInfo.getType() == type)
				.sorted(comparing(ItemMetadata::getName));
	}

}
