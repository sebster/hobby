package com.sebster.remarkable.cli.commands;

import java.util.List;

import com.sebster.remarkable.cloudapi.RemarkableClient;
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
		List<RemarkableClient> clients = cli.getClientManager().listClients();
		for (int i = 0; i < clients.size(); i++) {
			cli.println((i + 1) + ") " + clients.get(i));
		}
	}

}
