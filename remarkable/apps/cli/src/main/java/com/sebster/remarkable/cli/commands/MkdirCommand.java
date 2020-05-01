package com.sebster.remarkable.cli.commands;

import static com.sebster.remarkable.cloudapi.RemarkablePath.parsePaths;

import java.util.List;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "mkdir",
		mixinStandardHelpOptions = true,
		description = "Make new folders",
		version = "1.0"
)
public class MkdirCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(
			index = "0",
			paramLabel = "folder",
			description = "The new folder(s) to create.",
			arity = "0..*"
	)
	private List<String> paths;

	@Override
	public void run() {
		cli.getSelectedClient().createFolders(parsePaths(paths));
	}

}
