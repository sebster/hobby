package com.sebster.remarkable.backup.domain;

import java.io.InputStream;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableDocument;
import com.sebster.remarkable.cloudapi.RemarkableFolder;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkableRootFolder;
import lombok.NonNull;

public interface RemarkableBackupStorageService {

	RemarkableRootFolder list(@NonNull UUID clientId);

	void storeFolder(@NonNull UUID clientId, @NonNull RemarkableFolder folder);

	void storeDocument(@NonNull UUID clientId, @NonNull RemarkableDocument document, @NonNull InputStream data);

	void deleteItem(@NonNull UUID clientId, RemarkableItem remarkableItem);

}
