package com.sebster.remarkable.cli.commands.completion;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.jline.builtins.Completers.SystemCompleter;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.ArgumentCompleter;

import lombok.NonNull;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;

public class CommandCompleterBuilder {

	private final @NonNull CommandSpec commandSpec;
	private final @NonNull SystemCompleter completer;
	private final @NonNull List<Completer> argumentCompleters = new ArrayList<>();

	private CommandCompleterBuilder(Class<?> command) {
		commandSpec = new CommandLine(command).getCommandSpec();
		completer = new SystemCompleter();
		completer.addAliases(Stream.of(commandSpec.aliases()).collect(toMap(alias -> alias, alias -> commandSpec.name())));
	}

	public SystemCompleter build() {
		completer.add(commandSpec.name(), new ArgumentCompleter(argumentCompleters));
		return completer;
	}

	public static CommandCompleterBuilder command(@NonNull Class<?> command) {
		return new CommandCompleterBuilder(command);
	}

	public CommandCompleterBuilder argumentCompleter(@NonNull Completer completer) {
		argumentCompleters.add(completer);
		return this;
	}

}
