package com.sebster.remarkable.backup.infrastructure;

import java.util.Collection;
import java.util.UUID;

import com.sebster.commons.io.InputStreamProcessor;
import com.sebster.remarkable.backup.domain.RemarkableBackupStorageService;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import com.sebster.remarkable.cloudapi.RemarkableRoot;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

// TOOD: Remove RemarkableBackupStorageService API and interact with RemarkableClient API instead.

@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RemarkableBackupClientImpl implements RemarkableClient {

	@Getter
	private final UUID id;

	private final RemarkableBackupStorageService storageService;

	@Override
	public String getDescription() {
		return "Backup of: " + id;
	}

	@Override
	public RemarkableRoot list() {
		return storageService.list(id);
	}

	@Override
	public void download(@NonNull RemarkableItem item, @NonNull InputStreamProcessor processor) {
		// TODO: Implement.
		throw new UnsupportedOperationException();
	}

	@Override
	public void createFolders(@NonNull RemarkableCollection parent, @NonNull Collection<RemarkablePath> paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(@NonNull Collection<? extends RemarkableItem> items, boolean recursive) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearCaches() {
	}

	@Override
	public String toString() {
		return getDescription();
	}

}
