package com.sebster.remarkable.cli.commands;

import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import org.jline.builtins.Completers.SystemCompleter;

import com.sebster.remarkable.cli.commands.completion.RemarkableClientCompleter;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.NonNull;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "select", description = "Select a client")
public class SelectCommand extends BaseCommand {

	@Parameters(index = "0", paramLabel = "client", description = "The client to select.")
	private String clientDescription;

	@Override
	public void run() {
		cli.selectClient(clientDescription);
	}

	public static SystemCompleter completer(@NonNull RemarkableClientManager clientManager) {
		return commandCompleter(SelectCommand.class)
				.argumentCompleter(new RemarkableClientCompleter(clientManager))
				.build();
	}

}
