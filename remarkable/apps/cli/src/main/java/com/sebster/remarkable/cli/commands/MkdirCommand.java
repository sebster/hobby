package com.sebster.remarkable.cli.commands;

import com.sebster.remarkable.cloudapi.RemarkablePath;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "mkdir",
		mixinStandardHelpOptions = true,
		description = "Create a directory",
		version = "1.0"
)
public class MkdirCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(
			index = "0",
			paramLabel = "path",
			description = "The directory path to create.",
			arity = "0"

	)
	private String path;

	@Override
	public void run() {
		cli.doWithClient(client -> client.createFolders(RemarkablePath.parse(path)));
	}

}
