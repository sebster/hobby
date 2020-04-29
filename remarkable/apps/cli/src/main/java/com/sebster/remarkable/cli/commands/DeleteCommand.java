package com.sebster.remarkable.cli.commands;

import static com.sebster.commons.collections.Lists.map;

import java.util.List;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import com.sebster.remarkable.cloudapi.RemarkableRootFolder;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "delete",
		aliases = "rm",
		mixinStandardHelpOptions = true,
		description = "Delete a file or folder",
		version = "1.0"
)
public class DeleteCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(
			paramLabel = "file",
			description = "The file or folder id or name.",
			arity = "1..*"
	)
	private List<String> files;

	@Option(names = { "-r", "-R", "--recursive" }, description = "Recursively delete folders.")
	private boolean recursive;

	@Override
	public void run() {
		cli.doWithClient(client -> delete(client, files));
	}

	private void delete(RemarkableClient client, List<String> files) {
		RemarkableRootFolder root = client.list();
		List<RemarkableItem> items = map(files, file -> root.getItem(RemarkablePath.parse(file)));
		client.delete(items, recursive);
	}

}
