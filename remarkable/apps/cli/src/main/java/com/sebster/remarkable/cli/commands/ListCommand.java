package com.sebster.remarkable.cli.commands;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "list",
		mixinStandardHelpOptions = true,
		description = "List the contents of the reMarkable",
		version = "1.0"
)
public class ListCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Override
	public void run() {
		cli.doWithClient(this::printList);
	}

	private void printList(RemarkableClient client) {
		client.list().traverse().forEach(cli::println);
	}

}
