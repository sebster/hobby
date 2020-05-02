package com.sebster.remarkable.cli.commands;

import java.util.List;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "delete",
		aliases = "rm",
		mixinStandardHelpOptions = true,
		description = "Delete a document or folder",
		version = "1.0"
)
public class DeleteCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(
			paramLabel = "item",
			description = "The documents(s) or folder(s) to delete.",
			arity = "1..*"
	)
	private List<String> paths;

	@Option(names = { "-r", "-R", "--recursive" }, description = "Recursively delete folders.")
	private boolean recursive;

	@Override
	public void run() {
		cli.getSelectedClient().delete(cli.getItems(paths), recursive);
	}

}
