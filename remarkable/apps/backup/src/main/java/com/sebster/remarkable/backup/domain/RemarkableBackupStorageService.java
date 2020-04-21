package com.sebster.remarkable.backup.domain;

import com.sebster.remarkable.cloudapi.RemarkableDocument;
import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import com.sebster.remarkable.cloudapi.RemarkableFolder;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkableRootFolder;
import lombok.NonNull;

public interface RemarkableBackupStorageService {

	RemarkableRootFolder list();

	void storeFolder(@NonNull RemarkableFolder folder);

	void storeDocument(@NonNull RemarkableDocument document, @NonNull RemarkableDownloadLink downloadLink);

	void deleteItem(RemarkableItem remarkableItem);

}
