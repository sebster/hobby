package com.sebster.remarkable.cli.commands;

import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import org.jline.builtins.Completers.SystemCompleter;

import com.sebster.remarkable.cli.commands.completion.ClientsCompleter;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
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
			description = "The client (start of id, part of description).",
			arity = "0..1"

	)
	private String selector;

	@Override
	public void run() {
		if (selector != null) {
			cli.doWithClient(selector.trim(), cli::selectClient);
		} else {
			cli.doWithClient(cli::println);
		}
	}

	public static SystemCompleter completer(RemarkableClientManager clientManager) {
		return commandCompleter(ClientCommand.class)
				.argumentCompleter(new ClientsCompleter(clientManager))
				.build();
	}

}
