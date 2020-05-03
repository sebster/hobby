package com.sebster.remarkable.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "clients", description = "List the registered clients")
public class ClientsCommand extends BaseCommand {

	@Override
	public void run() {
		cli.getClientManager().listClients().forEach(cli::println);
	}

}
