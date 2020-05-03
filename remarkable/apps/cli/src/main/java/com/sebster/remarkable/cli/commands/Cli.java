package com.sebster.remarkable.cli.commands;

import static com.sebster.commons.collections.Lists.map;
import static com.sebster.remarkable.cloudapi.RemarkablePath.parsePaths;
import static org.jline.utils.AttributedStyle.BOLD;
import static org.jline.utils.AttributedStyle.CYAN;
import static org.jline.utils.AttributedStyle.DEFAULT;
import static org.jline.utils.AttributedStyle.GREEN;
import static org.jline.utils.AttributedStyle.RED;
import static org.jline.utils.AttributedStyle.YELLOW;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jline.builtins.Completers.SystemCompleter;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import com.sebster.remarkable.cli.commands.completion.CompletionContext;
import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableException;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

@Command(
		name = "",
		description = {
				"reMarkable Cloud API CLI. Hit @|magenta <TAB>|@ to see available commands.",
				"Type `@|bold,yellow keymap ^[s tailtip-toggle|@`, then hit @|magenta ALT-S|@ to toggle tailtips.",
				""
		},
		footer = { "", "Press Ctl-D to exit." },
		subcommands = {
				RegisterCommand.class,
				UnregisterCommand.class,
				ClientsCommand.class,
				SelectCommand.class,
				DeselectCommand.class,
				ListCommand.class,
				MkdirCommand.class,
				DownloadCommand.class,
				RmCommand.class,
				HelpCommand.class,
		}
)
@RequiredArgsConstructor
public class Cli implements Runnable, IExecutionExceptionHandler, CompletionContext {

	private final @NonNull Terminal terminal;

	@Getter
	private final @NonNull RemarkableClientManager clientManager;

	private RemarkableClient selectedClient;

	@Override
	public void run() {
		println(new CommandLine(this).getUsageMessage());
	}

	@Override
	public int handleExecutionException(Exception e, CommandLine commandLine, ParseResult parseResult) throws Exception {
		if (e instanceof RemarkableException) {
			println(withErrorStyle(e.getMessage()));
			return 1;
		}
		throw e;
	}

	public SystemCompleter completers() {
		SystemCompleter systemCompleter = new SystemCompleter();
		systemCompleter.add(SelectCommand.completer(this));
		systemCompleter.add(MkdirCommand.completer(this));
		systemCompleter.add(RmCommand.completer(this));
		systemCompleter.add(UnregisterCommand.completer(this));
		return systemCompleter;
	}

	public void select(@NonNull RemarkableClient client) {
		selectedClient = client;
	}

	public void selectClient(@NonNull String description) {
		select(getClient(description));
	}

	public void deselect(@NonNull RemarkableClient client) {
		if (client.equals(selectedClient)) {
			selectedClient = null;
		}
	}

	@Override
	public boolean hasSelectedClient() {
		return selectedClient != null;
	}

	@Override
	public RemarkableClient getSelectedClient() {
		return Optional.ofNullable(selectedClient).orElseThrow(() -> new RemarkableException("No client selected."));
	}
	}

	public RemarkableClient getClient(String description) {
		return description != null ? clientManager.getClient(description.trim()) : getSelectedClient();
	}

	public Optional<RemarkableClient> findClient(String description) {
		return description != null ? clientManager.findClient(description) : Optional.ofNullable(selectedClient);
	}

	public List<RemarkableItem> getItems(@NonNull Collection<String> paths) {
		RemarkableCollection root = getSelectedClient().list();
		return map(parsePaths(paths), root::getItem);
	}

	public void println(@NonNull Object object) {
		terminal.writer().println(object);
	}

	public void printf(@NonNull String format, Object... arguments) {
		terminal.writer().printf(format, arguments);
	}

	public String getPrompt() {
		return withStyle(BOLD, (selectedClient != null ? selectedClient.getDescription() : "") + "> ");
	}

	public String withStyle(@NonNull AttributedStyle style, @NonNull Object string) {
		return new AttributedStringBuilder().styled(style, string.toString()).toAnsi(terminal);
	}

	public String withItemIdStyle(@NonNull UUID itemId) {
		return withStyle(BOLD.foreground(CYAN), itemId);
	}

	public String withItemStyle(@NonNull RemarkableItem item) {
		return item.isFolder() ?
				withStyle(BOLD.foreground(RED), item.getPath()) + "/" :
				withStyle(BOLD.foreground(GREEN), item.getPath());
	}

	public Object withFileStyle(@NonNull File file) {
		return file.isDirectory() ?
				withStyle(BOLD.foreground(RED), file + "/") :
				withStyle(BOLD.foreground(GREEN), file.getPath());
	}

	public String withErrorStyle(@NonNull String message) {
		return withStyle(DEFAULT.foreground(YELLOW), "Error: " + message);
	}

}
