package com.sebster.remarkable.cli.commands;

import static com.sebster.commons.strings.Strings.isNotBlank;
import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import org.jline.builtins.Completers.SystemCompleter;

import com.sebster.remarkable.cli.commands.completion.RemarkableClientCompleter;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.NonNull;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "client",
		aliases = "select",
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
			description = "The client (part of description, start of id).",
			arity = "0..1"
	)
	private String selector;

	@Override
	public void run() {
		if (isNotBlank(selector)) {
			cli.selectClient(selector);
		} else {
			cli.println(cli.getSelectedClient());
		}
	}

	public static SystemCompleter completer(@NonNull RemarkableClientManager clientManager) {
		return commandCompleter(ClientCommand.class)
				.argumentCompleter(new RemarkableClientCompleter(clientManager))
				.build();
	}

}
