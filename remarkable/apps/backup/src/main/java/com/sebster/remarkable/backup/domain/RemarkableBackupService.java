package com.sebster.remarkable.backup.domain;

import java.util.Objects;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class RemarkableBackupService {

	private final @NonNull RemarkableClient client;
	private final @NonNull RemarkableBackupStorageService storageService;

	public void run() {
		RemarkableCollection remote = client.list();
		RemarkableCollection local = storageService.list();

		remote.traverse().forEach(remoteItem -> {
			int remoteVersion = remoteItem.getVersion();
			RemarkableItem localItem = local.findItem(remoteItem.getId()).orElse(null);
			if (localItem == null) {
				log.debug("new: (v{}) {}", remoteVersion, remoteItem);
				storeItem(remoteItem);
			} else {
				int localVersion = localItem.getVersion();
				if (remoteVersion > localVersion) {
					if (!Objects.equals(remoteItem.getPath(), localItem.getPath())) {
						log.debug("move (v{} -> v{}): {} -> {}", localVersion, remoteVersion, localItem, remoteItem);
					} else {
						log.debug("update (v{} -> v{}): {}", localVersion, remoteVersion, remoteItem);
					}
					storeItem(remoteItem);
				} else {
					log.debug("unchanged: (v{}) {}", remoteVersion, remoteItem);
				}
			}
		});

		local.traverse().forEach(localItem -> {
			RemarkableItem remoteItem = remote.findItem(localItem.getId()).orElse(null);
			if (remoteItem == null) {
				log.debug("deleted: (v{}) {}", localItem.getVersion(), localItem);
				storageService.deleteItem(remoteItem);
			}
		});
	}

	private void storeItem(RemarkableItem item) {
		item
				.withFolder(storageService::storeFolder)
				.withDocument(document -> storageService.storeDocument(document, getDownloadLink(item)));
	}

	private RemarkableDownloadLink getDownloadLink(RemarkableItem item) {
		return item.getDownloadLink().orElseGet(() -> client.downloadLink(item.getId()));
	}

}
