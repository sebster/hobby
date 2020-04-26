package com.sebster.remarkable.cli;

import static java.util.Arrays.copyOfRange;
import static org.jline.builtins.Builtins.Command.TTOP;
import static org.jline.builtins.Widgets.CmdLine.DescriptionType.COMMAND;
import static org.jline.reader.LineReader.LIST_MAX;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jline.builtins.Builtins;
import org.jline.builtins.Completers;
import org.jline.builtins.Options.HelpException;
import org.jline.builtins.Widgets;
import org.jline.builtins.Widgets.CmdDesc;
import org.jline.builtins.Widgets.CmdLine;
import org.jline.builtins.Widgets.TailTipWidgets.TipType;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sebster.remarkable.cli.commands.Cli;
import com.sebster.remarkable.cli.commands.ClientCommand;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliCommands;

@SpringBootApplication
@AllArgsConstructor
public class RemarkableCli implements CommandLineRunner {

	private final @NonNull RemarkableClientManager clientManager;

	@Override
	public void run(String... args) throws IOException {
		Terminal terminal = TerminalBuilder.builder().build();

		// Set up JLine built-in commands.
		Path workDir = Paths.get("");
		Builtins builtins = new Builtins(workDir, null, null);
		builtins.rename(TTOP, "top");
		builtins.alias("zle", "widget");
		builtins.alias("bindkey", "keymap");
		Completers.SystemCompleter systemCompleter = builtins.compileCompleters();

		// Set up picocli commands.
		Cli cli = new Cli(terminal, clientManager);
		CommandLine cmd = new CommandLine(cli);
		PicocliCommands picocliCommands = new PicocliCommands(workDir, cmd);
		systemCompleter.add(picocliCommands.compileCompleters());
		systemCompleter.add(ClientCommand.completer(clientManager));
		systemCompleter.compile();
		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.completer(systemCompleter)
				.parser(new DefaultParser())
				.variable(LIST_MAX, 50)   // max tab completion candidates
				.build();
		builtins.setLineReader(reader);
		DescriptionGenerator descriptionGenerator = new DescriptionGenerator(builtins, picocliCommands);
		new Widgets.TailTipWidgets(reader, descriptionGenerator::commandDescription, 5, TipType.COMPLETER);

		// Start the shell and process input until the user quits with Ctl-D.
		String line;
		while (true) {
			try {
				line = reader.readLine(cli.getPrompt(), null, (MaskingCallback) null, null);
				if (line.matches("^\\s*#.*")) {
					continue;
				}
				ParsedLine pl = reader.getParser().parse(line, 0);
				String[] arguments = pl.words().toArray(new String[0]);
				String command = Parser.getCommand(pl.word());
				if (builtins.hasCommand(command)) {
					builtins.execute(command, copyOfRange(arguments, 1, arguments.length), System.in, System.out, System.err);
				} else {
					new CommandLine(cli).execute(arguments);
				}
			} catch (HelpException e) {
				HelpException.highlight(e.getMessage(), HelpException.defaultStyle()).print(terminal);
			} catch (UserInterruptException e) {
				// Ignore
			} catch (EndOfFileException e) {
				return;
			} catch (Exception e) {
				AttributedStringBuilder asb = new AttributedStringBuilder();
				asb.append(e.getMessage(), AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
				asb.toAttributedString().println(terminal);
			}
		}
	}

	/**
	 * Provide command descriptions for JLine TailTipWidgets to be displayed in the status bar.
	 */
	@AllArgsConstructor
	private static class DescriptionGenerator {

		private final Builtins builtins;
		private final PicocliCommands picocli;

		CmdDesc commandDescription(CmdLine line) {
			CmdDesc out = null;
			if (line.getDescriptionType() == COMMAND) {
				String cmd = Parser.getCommand(line.getArgs().get(0));
				if (builtins.hasCommand(cmd)) {
					out = builtins.commandDescription(cmd);
				} else if (picocli.hasCommand(cmd)) {
					out = picocli.commandDescription(cmd);
				}
			}
			return out;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(RemarkableCli.class, args);
	}

}