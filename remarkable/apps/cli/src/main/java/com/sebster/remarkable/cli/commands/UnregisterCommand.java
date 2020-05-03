package com.sebster.remarkable.cli.commands;

import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import org.jline.builtins.Completers.SystemCompleter;

import com.sebster.remarkable.cli.commands.completion.RemarkableClientCompleter;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.NonNull;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "unregister", description = "Unregister the selected or specified client")
public class UnregisterCommand extends BaseCommand {

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

	public static SystemCompleter completer(@NonNull RemarkableClientManager clientManager) {
		return commandCompleter(UnregisterCommand.class)
				.argumentCompleter(new RemarkableClientCompleter(clientManager))
				.build();
	}

}
