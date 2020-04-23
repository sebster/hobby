package com.sebster.remarkable.backup.domain;

import static com.sebster.remarkable.backup.domain.RemarkableBackupType.FULL;

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

	public void backup(@NonNull UUID clientId, @NonNull RemarkableBackupType type) {

		RemarkableClient client = clientManager.getClient(clientId);
		log.info("Starting {} backup: {} ({})", type, client.getDescription(), clientId);

		RemarkableCollection remote = client.list();
		RemarkableCollection local = storageService.list(clientId);

		remote.traverse().forEach(remoteItem -> {
			int remoteVersion = remoteItem.getVersion();
			RemarkableItem localItem = local.findItem(remoteItem.getId()).orElse(null);
			if (localItem == null) {
				log.debug("New: (v{}) {}", remoteVersion, remoteItem);
				storeItem(remoteItem, client);
			} else {
				int localVersion = localItem.getVersion();
				if (remoteVersion > localVersion) {
					if (!Objects.equals(remoteItem.getPath(), localItem.getPath())) {
						log.debug("Moved (v{} -> v{}): {} -> {}", localVersion, remoteVersion, localItem, remoteItem);
					} else {
						log.debug("Changed (v{} -> v{}): {}", localVersion, remoteVersion, remoteItem);
					}
					storeItem(remoteItem, client);
				} else if (remoteVersion == localVersion && type == FULL) {
					log.debug("Refresh: (v{}) {}", remoteVersion, remoteItem);
					storeItem(remoteItem, client);
				} else {
					log.debug("Unchanged: (v{}) {}", remoteVersion, remoteItem);
				}
			}
		});

		local.traverse().forEach(localItem -> {
			RemarkableItem remoteItem = remote.findItem(localItem.getId()).orElse(null);
			if (remoteItem == null) {
				log.debug("Deleted: (v{}) {}", localItem.getVersion(), localItem);
				storageService.deleteItem(clientId, localItem);
			}
		});

		log.info("Backup complete.");
	}

	private void storeItem(RemarkableItem item, RemarkableClient client) {
		item
				.withFolder(folder -> storageService.storeFolder(client.getId(), folder))
				.withDocument(document -> storageService.storeDocument(client.getId(), document, getDownloadLink(item, client)));
	}

	private RemarkableDownloadLink getDownloadLink(RemarkableItem item, RemarkableClient client) {
		return item.getDownloadLink().orElseGet(() -> client.downloadLink(item.getId()));
	}

}
