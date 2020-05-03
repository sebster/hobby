package com.sebster.remarkable.cli.commands.completion;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;

public interface CompletionContext {

	RemarkableClientManager getClientManager();

	boolean hasSelectedClient();

	RemarkableClient getSelectedClient();

}
