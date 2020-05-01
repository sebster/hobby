package com.sebster.remarkable.cli.commands.completion;

import static java.util.function.Function.identity;
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
	private final @NonNull SystemCompleter systemCompleter;
	private final @NonNull List<Completer> argumentCompleters = new ArrayList<>();

	private CommandCompleterBuilder(Class<?> command) {
		commandSpec = new CommandLine(command).getCommandSpec();
		systemCompleter = new SystemCompleter();
		systemCompleter.addAliases(Stream.of(commandSpec.aliases()).collect(toMap(identity(), alias -> commandSpec.name())));
	}

	public SystemCompleter build() {
		ArgumentCompleter argumentCompleter = new ArgumentCompleter(argumentCompleters);
		commandSpec.names().forEach(name -> systemCompleter.add(name, argumentCompleter));
		return systemCompleter;
	}

	public static CommandCompleterBuilder commandCompleter(@NonNull Class<?> command) {
		return new CommandCompleterBuilder(command);
	}

	public CommandCompleterBuilder argumentCompleter(@NonNull Completer completer) {
		argumentCompleters.add(completer);
		return this;
	}

}
