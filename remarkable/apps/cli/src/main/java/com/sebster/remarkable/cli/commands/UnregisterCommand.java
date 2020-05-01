package com.sebster.remarkable.cli.commands;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "unregister",
		mixinStandardHelpOptions = true,
		description = "Unregister the selected or specified client",
		version = "1.0"
)
public class UnregisterCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(
			index = "0",
			paramLabel = "client",
			description = "The client to unregister.",
			arity = "0..1"
	)
	private String selector;

	@Override
	public void run() {
		RemarkableClient client = cli.getClient(selector);
		cli.getClientManager().unregister(client);
		cli.println("Unregistered " + client);
		cli.deselect(client);
	}

}
