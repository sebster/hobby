package com.sebster.remarkable.backup.infrastructure;

import static com.sebster.remarkable.backup.infrastructure.ItemType.DOCUMENT;
import static com.sebster.remarkable.backup.infrastructure.ItemType.FOLDER;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebster.remarkable.backup.domain.RemarkableBackupStorageService;
import com.sebster.remarkable.cloudapi.RemarkableDocument;
import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import com.sebster.remarkable.cloudapi.RemarkableFolder;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkableRootFolder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class FileSystemRemarkableBackupStorageService implements RemarkableBackupStorageService {

	private static final String METADATA_FILE = "metadata.json";

	private final @NonNull File location;
	private final @NonNull ObjectMapper mapper;

	@PostConstruct
	public void init() {
		if (!location.exists() && !location.mkdirs()) {
			throw new IllegalStateException("Cannot create backup location: " + location);
		}
		if (!location.isDirectory()) {
			throw new IllegalStateException("Backup location must be a directory: " + location);
		}
	}

	@Override
	public RemarkableRootFolder list() {
		return new ItemMetadataListUnmarshaller(loadMetadata()).unmarshal();
	}

	@Override
	public void storeFolder(RemarkableFolder folder) {
		Map<UUID, ItemMetadata> metadata = loadMetadataMap();
		ItemMetadata itemMetadata = metadata.computeIfAbsent(folder.getId(), id -> new ItemMetadata());
		updateFolderMetadata(itemMetadata, folder);
		saveMetadataMap(metadata);
	}

	@Override
	public void storeDocument(RemarkableDocument document, RemarkableDownloadLink downloadLink) {
		Map<UUID, ItemMetadata> metadata = loadMetadataMap();
		ItemMetadata itemMetadata = metadata.computeIfAbsent(document.getId(), id -> new ItemMetadata());
		updateDocumentMetadata(itemMetadata, document);
		saveMetadataMap(metadata);
		downloadDocumentData(document, downloadLink);
	}

	@Override
	public void deleteFolder(RemarkableFolder folder) {
		deleteItemMetadata(folder);
	}

	@Override
	public void deleteDocument(RemarkableDocument document) {
		deleteItemMetadata(document);
		deleteDocumentData(document);
	}

	private void updateFolderMetadata(ItemMetadata itemMetadata, RemarkableFolder folder) {
		itemMetadata.setType(FOLDER);
		updateItemMetadata(itemMetadata, folder);
	}

	private void updateDocumentMetadata(ItemMetadata itemMetadata, RemarkableDocument document) {
		itemMetadata.setType(DOCUMENT);
		updateItemMetadata(itemMetadata, document);
		itemMetadata.setBookmarked(document.isBookmarked());
		itemMetadata.setCurrentPage(document.getCurrentPage());
	}

	private void updateItemMetadata(ItemMetadata itemMetadata, RemarkableItem item) {
		itemMetadata.setId(item.getId());
		itemMetadata.setVersion(item.getVersion());
		itemMetadata.setParentId(item.getParent().map(RemarkableItem::getId).orElse(null));
		itemMetadata.setName(item.getName());
		itemMetadata.setModificationTime(item.getModificationTime());
	}

	private void downloadDocumentData(RemarkableDocument document, RemarkableDownloadLink downloadLink) {
		File documentFile = getDocumentFile(document);
		try {
			copyInputStreamToFile(downloadLink.getUrl().openStream(), documentFile);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void deleteDocumentData(RemarkableDocument document) {
		File documentFile = getDocumentFile(document);
		if (!documentFile.delete()) {
			throw new UncheckedIOException(new IOException("Could not delete file: " + documentFile));
		}
	}

	public void deleteItemMetadata(RemarkableItem item) {
		Map<UUID, ItemMetadata> metadata = loadMetadataMap();
		metadata.remove(item.getId());
		saveMetadataMap(metadata);
	}

	private List<ItemMetadata> loadMetadata() {
		File metadataFile = getMetadataFile();
		if (!metadataFile.exists()) {
			return emptyList();
		}
		try {
			return mapper.readValue(metadataFile, mapper.getTypeFactory().constructCollectionType(List.class, ItemMetadata.class));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void saveMetadata(Collection<ItemMetadata> metadata) {
		try {
			mapper.writeValue(getMetadataFile(), metadata);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private Map<UUID, ItemMetadata> loadMetadataMap() {
		return loadMetadata().stream().collect(toMap(ItemMetadata::getId, identity()));
	}

	private void saveMetadataMap(Map<UUID, ItemMetadata> metadata) {
		saveMetadata(metadata.values());
	}

	private File getMetadataFile() {
		return new File(location, METADATA_FILE);
	}

	private File getDocumentFile(RemarkableDocument document) {
		return new File(location, document.getId().toString() + ".zip");
	}

}
