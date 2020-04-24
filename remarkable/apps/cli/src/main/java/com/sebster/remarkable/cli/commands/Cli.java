package com.sebster.remarkable.cli.commands;

import java.util.Optional;
import java.util.function.Consumer;

import org.jline.terminal.Terminal;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Command(
		name = "",
		description = {
				"reMarkable Cloud API CLI. " +
						"Hit @|magenta <TAB>|@ to see available commands.",
				"Type `@|bold,yellow keymap ^[s tailtip-toggle|@`, " +
						"then hit @|magenta ALT-S|@ to toggle tailtips.",
				"" },
		footer = { "", "Press Ctl-D to exit." },
		subcommands = {
				ClientsCommand.class,
				ClientCommand.class,
				RegisterCommand.class,
				UnregisterCommand.class,
				HelpCommand.class,
		}
)
@RequiredArgsConstructor
public class Cli implements Runnable {

	private final @NonNull Terminal terminal;

	@Getter
	private final @NonNull RemarkableClientManager clientManager;

	@Setter
	private RemarkableClient client;

	public Optional<RemarkableClient> getClient() {
		return Optional.ofNullable(client);
	}

	public void run() {
		println(new CommandLine(this).getUsageMessage());
	}

	public void println(Object object) {
		terminal.writer().println(object);
	}

	public void withClient(Consumer<RemarkableClient> action) {
		getClient().ifPresentOrElse(action, () -> println("No client selected."));
	}

	public void withClient(String selector, Consumer<RemarkableClient> action) {
		clientManager.findClient(selector).ifPresentOrElse(action, () -> println("Unknown client: " + selector));
	}

}
