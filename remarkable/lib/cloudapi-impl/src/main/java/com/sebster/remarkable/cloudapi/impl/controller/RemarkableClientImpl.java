package com.sebster.remarkable.cloudapi.impl.controller;

import static com.sebster.commons.collections.Lists.filter;
import static com.sebster.commons.collections.Lists.filterAndMap;
import static com.sebster.commons.collections.Lists.map;
import static com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto.FOLDER_TYPE;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

import java.io.InputStream;
import java.time.Clock;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import com.sebster.commons.strings.Strings;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import com.sebster.remarkable.cloudapi.RemarkableRoot;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RemarkableClientImpl implements RemarkableClient {

	private final @NonNull Clock clock;
	private final @NonNull RemarkableClientInfo info;
	private final @NonNull RemarkableApiClient apiClient;

	private RemarkableRoot cachedRoot;

	@Override
	@EqualsAndHashCode.Include
	public UUID getId() {
		return info.getClientId();
	}

	@Override
	public String getDescription() {
		return info.getDescription();
	}

	@Override
	public RemarkableRoot list() {
		if (cachedRoot != null) {
			return cachedRoot;
		}
		String sessionToken = apiClient.login(info.getLoginToken());
		cachedRoot = new ItemInfoDtoListUnmarshaller(this, apiClient.list(sessionToken, true)).unmarshal();
		return cachedRoot;
	}

	@Override
	public InputStream download(@NonNull RemarkableItem item) {
		return item.getDownloadLink().orElseThrow(() -> new IllegalArgumentException("No download link: " + item)).download();
	}

	@Override
	public void createFolders(@NonNull RemarkableCollection parent, @NonNull Collection<RemarkablePath> paths) {
		Map<RemarkablePath, ItemInfoDto> folderInfos = new LinkedHashMap<>();
		for (RemarkablePath path : paths) {
			// Navigate the path prefix until the component no longer exists.
			RemarkableCollection newParent = parent;
			while (path.isNotEmpty() && newParent.hasFolder(path.getHead())) {
				newParent = newParent.getFolder(path.getHead());
				path = path.getTail();
			}

			// Create the folder creation request DTOs.
			Instant modificationTime = clock.instant();
			RemarkablePath parentPath = newParent.getPath();
			UUID parentId = newParent.isFolder() ? newParent.asFolder().getId() : null;
			while (path.isNotEmpty()) {
				UUID folderId = randomUUID();
				RemarkablePath folderPath = parentPath.append(path.getHead());
				folderInfos.putIfAbsent(folderPath,
						ItemInfoDto.builder()
								.id(folderId)
								.version(1)
								.modificationTime(modificationTime)
								.type(FOLDER_TYPE)
								.name(path.getHead())
								.parentId(parentId)
								.build()
				);
				parentPath = folderPath;
				parentId = folderInfos.get(parentPath).getId();
				path = path.getTail();
			}
		}

		// If any folders need to be created, invoke the API and check the results.
		if (!folderInfos.isEmpty()) {
			String sessionToken = apiClient.login(info.getLoginToken());
			apiClient.updateMetadata(sessionToken, folderInfos.values()).forEach(ErrorDto::throwOnError);
			clearCaches();
		}
	}

	@Override
	public void delete(@NonNull Collection<? extends RemarkableItem> items, boolean recursive) {
		var folders = filterAndMap(items, RemarkableItem::isFolder, RemarkableItem::asFolder);
		if (!recursive) {
			var nonEmptyFolders = filter(folders, RemarkableCollection::isNotEmpty);
			if (!nonEmptyFolders.isEmpty()) {
				throw new IllegalArgumentException("Folders not empty: " + Strings.join(", ", nonEmptyFolders));
			}
		}

		var foldersRecursive = folders.stream().flatMap(RemarkableCollection::recurse);
		var documents = items.stream().filter(RemarkableItem::isDocument);
		var itemsToDelete = Stream.concat(foldersRecursive, documents).distinct().collect(toList());

		// If any items need to be deleted, invoke the API and check the results.
		if (!itemsToDelete.isEmpty()) {
			String sessionToken = apiClient.login(info.getLoginToken());
			apiClient.delete(sessionToken, map(itemsToDelete, ItemInfoDto::fromItem)).forEach(ErrorDto::throwOnError);
			clearCaches();
		}
	}

	@Override
	public void clearCaches() {
		this.cachedRoot = null;
	}

	@Override
	public String toString() {
		return info.getDescription();
	}

}
