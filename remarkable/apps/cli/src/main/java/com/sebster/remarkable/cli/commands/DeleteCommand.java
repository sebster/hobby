package com.sebster.remarkable.cli.commands;

import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.jline.builtins.Completers.SystemCompleter;

import com.sebster.remarkable.cli.commands.completion.RemarkableItemCompleter;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import lombok.NonNull;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "delete",
		aliases = "rm",
		mixinStandardHelpOptions = true,
		description = "Delete a document or folder",
		version = "1.0"
)
public class DeleteCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(
			paramLabel = "item",
			description = "The documents(s) or folder(s) to delete.",
			arity = "1..*"
	)
	private List<String> paths;

	@Option(names = { "-r", "-R", "--recursive" }, description = "Recursively delete folders.")
	private boolean recursive;

	@Override
	public void run() {
		cli.getSelectedClient().delete(cli.getItems(paths), recursive);
	}

	public static SystemCompleter completer(@NonNull Function<String, Optional<RemarkableClient>> clientProvider) {
		return commandCompleter(DeleteCommand.class)
				.argumentCompleter(new RemarkableItemCompleter(clientProvider, null, true))
				.build();
	}

}
