package com.sebster.remarkable.backup.domain;

import static com.sebster.remarkable.backup.domain.RemarkableBackupType.FULL;

import java.util.Objects;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class RemarkableBackupService {

	private final @NonNull RemarkableBackupStorageService storageService;

	public void backup(@NonNull RemarkableClient client, @NonNull RemarkableBackupType type) {

		log.info("Starting {} backup: {}", type, client);

		RemarkableCollection remote = client.list();
		RemarkableCollection local = storageService.list(client.getId());

		remote.recurse().forEach(remoteItem -> {
			int remoteVersion = remoteItem.getVersion();
			RemarkableItem localItem = local.findItem(remoteItem.getId()).orElse(null);
			if (localItem == null) {
				log.debug("New: (v{}) {}", remoteVersion, remoteItem);
				storeItem(client, remoteItem);
			} else {
				int localVersion = localItem.getVersion();
				if (remoteVersion > localVersion) {
					if (!Objects.equals(remoteItem.getPath(), localItem.getPath())) {
						log.debug("Moved (v{} -> v{}): {} -> {}", localVersion, remoteVersion, localItem, remoteItem);
					} else {
						log.debug("Changed (v{} -> v{}): {}", localVersion, remoteVersion, remoteItem);
					}
					storeItem(client, remoteItem);
				} else if (remoteVersion == localVersion && type == FULL) {
					log.debug("Refresh: (v{}) {}", remoteVersion, remoteItem);
					storeItem(client, remoteItem);
				} else {
					log.debug("Unchanged: (v{}) {}", remoteVersion, remoteItem);
				}
			}
		});

		local.recurse().forEach(localItem -> {
			RemarkableItem remoteItem = remote.findItem(localItem.getId()).orElse(null);
			if (remoteItem == null) {
				log.debug("Deleted: (v{}) {}", localItem.getVersion(), localItem);
				storageService.deleteItem(client.getId(), localItem);
			}
		});

		log.info("Backup complete.");
	}

	private void storeItem(RemarkableClient client, RemarkableItem item) {
		item.doWithItem(
				folder -> storageService.storeFolder(client.getId(), folder),
				document -> client.download(document, data -> storageService.storeDocument(client.getId(), document, data))
		);
	}

}
