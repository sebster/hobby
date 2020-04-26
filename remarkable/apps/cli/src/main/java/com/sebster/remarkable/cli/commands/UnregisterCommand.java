package com.sebster.remarkable.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "unregister",
		mixinStandardHelpOptions = true,
		description = "Unregister the selected client",
		version = "1.0"
)
public class UnregisterCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Override
	public void run() {
		cli.doWithClient(client -> {
			cli.getClientManager().unregister(client);
			cli.println("Unregistered " + client);
			cli.setClient(null);
		});
	}

}
