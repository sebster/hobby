package com.sebster.remarkable.cli.commands;

import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import org.jline.builtins.Completers;

import com.sebster.remarkable.cli.commands.completion.RemarkableClientCompleter;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.NonNull;
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
	private String description;

	@Override
	public void run() {
		RemarkableClient client = cli.getClient(description);
		cli.getClientManager().unregister(client);
		cli.println("Unregistered " + client);
		cli.deselect(client);
	}

	public static Completers.SystemCompleter completer(@NonNull RemarkableClientManager clientManager) {
		return commandCompleter(UnregisterCommand.class)
				.argumentCompleter(new RemarkableClientCompleter(clientManager))
				.build();
	}

}
