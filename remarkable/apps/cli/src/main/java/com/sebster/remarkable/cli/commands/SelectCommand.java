package com.sebster.remarkable.cli.commands;

import static com.sebster.commons.strings.Strings.isNotBlank;
import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import org.jline.builtins.Completers.SystemCompleter;

import com.sebster.remarkable.cli.commands.completion.RemarkableClientCompleter;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.NonNull;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "select", description = "Select or deselect a client")
public class SelectCommand extends BaseCommand {

	@Parameters(
			index = "0",
			paramLabel = "client",
			description = "The client to select.",
			arity = "0..1"
	)
	private String description;

	@Override
	public void run() {
		if (isNotBlank(description)) {
			cli.selectClient(description);
		} else {
			cli.deselectSelectedClient();
		}
	}

	public static SystemCompleter completer(@NonNull RemarkableClientManager clientManager) {
		return commandCompleter(SelectCommand.class)
				.argumentCompleter(new RemarkableClientCompleter(clientManager))
				.build();
	}

}
