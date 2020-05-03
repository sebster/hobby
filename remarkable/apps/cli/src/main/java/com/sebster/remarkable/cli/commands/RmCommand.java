package com.sebster.remarkable.cli.commands;

import static com.sebster.remarkable.cli.commands.completion.CommandCompleterBuilder.commandCompleter;

import java.util.List;

import org.jline.builtins.Completers.SystemCompleter;

import com.sebster.remarkable.cli.commands.completion.CompletionContext;
import com.sebster.remarkable.cli.commands.completion.RemarkableItemCompleter;
import lombok.NonNull;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "rm", description = "Delete a document or folder")
public class RmCommand extends BaseCommand {

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

	public static SystemCompleter completer(@NonNull CompletionContext completionContext) {
		return commandCompleter(RmCommand.class)
				.argumentCompleter(new RemarkableItemCompleter(completionContext, null, true))
				.build();
	}

}
