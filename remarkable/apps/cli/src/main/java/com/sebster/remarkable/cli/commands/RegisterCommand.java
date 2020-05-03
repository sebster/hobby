package com.sebster.remarkable.cli.commands;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
		name = "register",
		description = {
				"Register a new client with a registration code",
				"Get a code at: https://my.remarkable.com/connect/desktop",
		}
)
public class RegisterCommand extends BaseCommand {

	@Parameters(index = "0", description = "The registration code.")
	private String code;

	@Parameters(index = "1", description = "The device description.")
	private String description;

	@Override
	public void run() {
		RemarkableClient client = cli.getClientManager().register(code, description);
		cli.println("Registered " + client);
		cli.select(client);
	}

}

