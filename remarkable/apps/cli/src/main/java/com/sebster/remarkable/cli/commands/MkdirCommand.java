package com.sebster.remarkable.cli.commands;

import static com.sebster.commons.collections.Lists.map;

import java.util.List;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkablePath;
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
			arity = "0..*"

	)
	private List<String> paths;

	@Override
	public void run() {
		cli.doWithClient(client -> createFolders(client, paths));
	}

	private void createFolders(RemarkableClient client, List<String> paths) {
		client.createFolders(map(paths, RemarkablePath::parse));
	}

}
