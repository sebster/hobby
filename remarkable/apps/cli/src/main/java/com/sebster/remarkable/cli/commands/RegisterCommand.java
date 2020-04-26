package com.sebster.remarkable.cli.commands;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "register",
		mixinStandardHelpOptions = true,
		description = {
				"Register a new client with a registration code",
				"Get a code at: https://my.remarkable.com/connect/desktop",
		},
		version = "1.0"
)
public class RegisterCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(index = "0", description = "The registration code.")
	private String code;

	@Parameters(index = "1", description = "The device description.")
	private String description;

	@Override
	public void run() {
		RemarkableClient client = cli.getClientManager().register(code, description);
		cli.println("Registered " + client);
		cli.selectClient(client);
	}

}
