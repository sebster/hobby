package com.sebster.remarkable.cli.commands.completion;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import lombok.NonNull;

public interface CompletionContext {

	RemarkableClientManager getClientManager();

	boolean hasSelectedClient();

	RemarkableClient getSelectedClient();

	RemarkablePath getWorkingDirectory(@NonNull RemarkableClient client);

}
