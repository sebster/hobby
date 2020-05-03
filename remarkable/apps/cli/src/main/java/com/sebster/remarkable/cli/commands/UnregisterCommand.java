package com.sebster.remarkable.cli.commands;

import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import org.jline.builtins.Completers.SystemCompleter;

import com.sebster.remarkable.cli.commands.completion.CompletionContext;
import com.sebster.remarkable.cli.commands.completion.RemarkableClientCompleter;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import lombok.NonNull;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "unregister", description = "Unregister a client or the selected client")
public class UnregisterCommand extends BaseCommand {

	@Parameters(index = "0", paramLabel = "client", description = "The client to unregister.", arity = "0..1")
	private String clientDescription;

	@Override
	public void run() {
		RemarkableClient client = cli.getClient(clientDescription);
		cli.getClientManager().unregister(client);
		cli.deselect(client);
	}

	public static SystemCompleter completer(@NonNull CompletionContext completionContext) {
		return commandCompleter(UnregisterCommand.class)
				.argumentCompleter(new RemarkableClientCompleter(completionContext))
				.build();
	}

}
