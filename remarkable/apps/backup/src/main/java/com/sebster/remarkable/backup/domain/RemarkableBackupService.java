package com.sebster.remarkable.backup.domain;

import java.util.Objects;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class RemarkableBackupService {

	private final @NonNull RemarkableClientManager clientManager;
	private final @NonNull RemarkableBackupStorageService storageService;

	public void backup(UUID clientId) {
		RemarkableClient client = clientManager.getClient(clientId);
		log.info("Backing up client {}: {}", client.getId(), client.getDescription());

		RemarkableCollection remote = client.list();
		RemarkableCollection local = storageService.list();

		remote.traverse().forEach(remoteItem -> {
			int remoteVersion = remoteItem.getVersion();
			RemarkableItem localItem = local.findItem(remoteItem.getId()).orElse(null);
			if (localItem == null) {
				log.debug("new: (v{}) {}", remoteVersion, remoteItem);
				storeItem(remoteItem, client);
			} else {
				int localVersion = localItem.getVersion();
				if (remoteVersion > localVersion) {
					if (!Objects.equals(remoteItem.getPath(), localItem.getPath())) {
						log.debug("move (v{} -> v{}): {} -> {}", localVersion, remoteVersion, localItem, remoteItem);
					} else {
						log.debug("update (v{} -> v{}): {}", localVersion, remoteVersion, remoteItem);
					}
					storeItem(remoteItem, client);
				} else {
					log.debug("unchanged: (v{}) {}", remoteVersion, remoteItem);
				}
			}
		});

		local.traverse().forEach(localItem -> {
			RemarkableItem remoteItem = remote.findItem(localItem.getId()).orElse(null);
			if (remoteItem == null) {
				log.debug("deleted: (v{}) {}", localItem.getVersion(), localItem);
				storageService.deleteItem(localItem);
			}
		});

		log.info("Backup complete.");
	}

	private void storeItem(RemarkableItem item, RemarkableClient client) {
		item
				.withFolder(storageService::storeFolder)
				.withDocument(document -> storageService.storeDocument(document, getDownloadLink(item, client)));
	}

	private RemarkableDownloadLink getDownloadLink(RemarkableItem item, RemarkableClient client) {
		return item.getDownloadLink().orElseGet(() -> client.downloadLink(item.getId()));
	}

}
