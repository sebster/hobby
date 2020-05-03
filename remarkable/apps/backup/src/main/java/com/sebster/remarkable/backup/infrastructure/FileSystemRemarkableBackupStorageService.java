package com.sebster.remarkable.backup.infrastructure;

import static com.sebster.remarkable.backup.infrastructure.ItemMetadata.ItemType.DOCUMENT;
import static com.sebster.remarkable.backup.infrastructure.ItemMetadata.ItemType.FOLDER;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebster.remarkable.backup.domain.RemarkableBackupStorageService;
import com.sebster.remarkable.cloudapi.RemarkableDocument;
import com.sebster.remarkable.cloudapi.RemarkableFolder;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkableRoot;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Remarkable backup storage service implementation which writes the backup to a directory on the file system.
 */
@AllArgsConstructor
@Slf4j
public class FileSystemRemarkableBackupStorageService implements RemarkableBackupStorageService {

	private static final String METADATA_FILE = "metadata.json";

	private final @NonNull File location;
	private final @NonNull ObjectMapper mapper;

	@PostConstruct
	public void init() {
		ensureDirectoryExists(location);
	}

	@Override
	public RemarkableRoot list(@NonNull UUID clientId) {
		return new ItemMetadataListUnmarshaller(loadMetadata(clientId)).unmarshal();
	}

	@Override
	public void storeFolder(@NonNull UUID clientId, @NonNull RemarkableFolder folder) {
		Map<UUID, ItemMetadata> metadata = loadMetadataMap(clientId);
		ItemMetadata itemMetadata = metadata.computeIfAbsent(folder.getId(), id -> new ItemMetadata());
		updateFolderMetadata(itemMetadata, folder);
		saveMetadataMap(clientId, metadata);
	}

	@Override
	public void storeDocument(@NonNull UUID clientId, @NonNull RemarkableDocument document, @NonNull InputStream data) {
		Map<UUID, ItemMetadata> metadata = loadMetadataMap(clientId);
		ItemMetadata itemMetadata = metadata.computeIfAbsent(document.getId(), id -> new ItemMetadata());
		updateDocumentMetadata(itemMetadata, document);
		saveData(clientId, document, data);
		saveMetadataMap(clientId, metadata);
	}

	@Override
	public void deleteItem(@NonNull UUID clientId, @NonNull RemarkableItem item) {
		Map<UUID, ItemMetadata> metadata = loadMetadataMap(clientId);
		metadata.remove(item.getId());
		deleteData(clientId, item);
		saveMetadataMap(clientId, metadata);
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
		itemMetadata.setParentId(item.getParentFolder().map(RemarkableItem::getId).orElse(null));
		itemMetadata.setName(item.getName());
		itemMetadata.setModificationTime(item.getModificationTime());
	}

	private void saveData(UUID clientId, RemarkableDocument document, InputStream inputStream) {
		try {
			copyInputStreamToFile(inputStream, getDataFile(clientId, document));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void deleteData(UUID clientId, RemarkableItem item) {
		File dataFile = getDataFile(clientId, item);
		if (dataFile.exists() && !dataFile.delete()) {
			throw new UncheckedIOException(new IOException("Could not delete file: " + dataFile));
		}
	}

	private List<ItemMetadata> loadMetadata(UUID clientId) {
		File metadataFile = getMetadataFile(clientId);
		if (!metadataFile.exists()) {
			return emptyList();
		}
		try {
			return mapper.readValue(metadataFile, mapper.getTypeFactory().constructCollectionType(List.class, ItemMetadata.class));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void saveMetadata(UUID clientId, Collection<ItemMetadata> metadata) {
		try {
			ensureDirectoryExists(getClientLocation(clientId));
			mapper.writeValue(getMetadataFile(clientId), metadata);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private Map<UUID, ItemMetadata> loadMetadataMap(UUID clientId) {
		return loadMetadata(clientId).stream().collect(toMap(ItemMetadata::getId, identity()));
	}

	private void saveMetadataMap(UUID clientId, Map<UUID, ItemMetadata> metadata) {
		saveMetadata(clientId, metadata.values());
	}

	private File getMetadataFile(UUID clientId) {
		return new File(getClientLocation(clientId), METADATA_FILE);
	}

	private File getDataFile(UUID clientId, RemarkableItem item) {
		return new File(getClientLocation(clientId), item.getId().toString() + ".zip");
	}

	private File getClientLocation(UUID clientId) {
		return new File(location, clientId.toString());
	}

	private void ensureDirectoryExists(File location) {
		if (!location.exists() && !location.mkdirs()) {
			throw new IllegalStateException("Cannot create backup location: " + location);
		}
		if (!location.isDirectory()) {
			throw new IllegalStateException("Backup location must be a directory: " + location);
		}
	}

}
