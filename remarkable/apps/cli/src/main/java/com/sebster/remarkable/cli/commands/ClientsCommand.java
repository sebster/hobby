package com.sebster.remarkable.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "clients",
		mixinStandardHelpOptions = true,
		description = "List the registered clients",
		version = "1.0"
)
public class ClientsCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Override
	public void run() {
		cli.getClientManager().listClients().forEach(cli::println);
	}

}
