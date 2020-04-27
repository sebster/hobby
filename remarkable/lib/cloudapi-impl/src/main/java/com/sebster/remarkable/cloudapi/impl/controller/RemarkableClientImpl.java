package com.sebster.remarkable.cloudapi.impl.controller;

import static com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto.FOLDER_TYPE;
import static java.util.UUID.randomUUID;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableFolder;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import com.sebster.remarkable.cloudapi.RemarkableRootFolder;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RemarkableClientImpl implements RemarkableClient {

	private final @NonNull Clock clock;
	private final @NonNull RemarkableClientInfo info;
	private final @NonNull RemarkableApiClient apiClient;

	@Override
	public UUID getId() {
		return info.getClientId();
	}

	@Override
	public String getDescription() {
		return info.getDescription();
	}

	@Override
	public RemarkableRootFolder list() {
		String sessionToken = apiClient.login(info.getLoginToken());
		return new ItemInfoDtoListUnmarshaller(apiClient.list(sessionToken, true)).unmarshal();
	}

	@Override
	public void createFolders(RemarkableFolder parent, @NonNull RemarkablePath path) {
		// Navigate the path prefix until the component no longer exists.
		RemarkableCollection collection = parent != null ? parent : list();
		while (path != null && collection.hasFolder(path.getHead())) {
			parent = collection.getFolder(path.getHead());
			collection = parent;
			path = path.getTail().orElse(null);
		}

		// Create the folder creation request DTOs.
		Instant modificationTime = clock.instant();
		UUID parentId = parent != null ? parent.getId() : null;
		List<ItemInfoDto> folderInfos = new ArrayList<>();
		while (path != null) {
			UUID folderId = randomUUID();
			folderInfos.add(
					ItemInfoDto.builder()
							.id(folderId)
							.version(1)
							.modificationTime(modificationTime)
							.type(FOLDER_TYPE)
							.name(path.getHead())
							.parentId(parentId)
							.build()
			);
			parentId = folderId;
			path = path.getTail().orElse(null);
		}

		// If any folders need to be created, invoke the API and check the results.
		if (!folderInfos.isEmpty()) {
			String sessionToken = apiClient.login(info.getLoginToken());
			checkResults(apiClient.updateMetadata(sessionToken, folderInfos));
		}
	}

	@Override
	public String toString() {
		return info.getDescription() + " (" + info.getClientId() + ")";
	}

	private void checkResults(List<ItemInfoDto> results) {
		for (ItemInfoDto result : results) {
			if (result.hasError()) {
				throw new RuntimeException(result.getErrorMessage().orElse(null));
			}
		}
	}

}
