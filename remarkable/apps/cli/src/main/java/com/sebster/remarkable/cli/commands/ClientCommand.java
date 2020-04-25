package com.sebster.remarkable.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "client",
		mixinStandardHelpOptions = true,
		description = "Select a client or show the selected client",
		version = "1.0"
)
public class ClientCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(
			index = "0",
			paramLabel = "client",
			description = "The client (number, start of id, part of description).",
			arity = "0..1"
	)
	private String selector;

	@Override
	public void run() {
		if (selector != null) {
			cli.doWithClient(selector, cli::setClient);
		} else {
			cli.doWithClient(cli::println);
		}
	}

}
