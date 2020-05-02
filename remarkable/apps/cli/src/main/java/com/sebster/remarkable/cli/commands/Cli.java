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

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import com.sebster.remarkable.cloudapi.RemarkableCollection;
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
				ClientsCommand.class,
				ClientCommand.class,
				RegisterCommand.class,
				UnregisterCommand.class,
				ListCommand.class,
				MkdirCommand.class,
				DownloadCommand.class,
				DeleteCommand.class,
				HelpCommand.class,
		}
)
@RequiredArgsConstructor
public class Cli implements Runnable, IExecutionExceptionHandler {

	private final @NonNull Terminal terminal;

	@Getter
	private final @NonNull RemarkableClientManager clientManager;

	private RemarkableClient client;

	public void run() {
		println(new CommandLine(this).getUsageMessage());
	}

	@Override
	public int handleExecutionException(Exception e, CommandLine commandLine, ParseResult parseResult) throws Exception {
		if (e.getMessage() == null) {
			throw e;
		}
		println(withErrorStyle(e.getMessage()));
		return 1;
	}

	public void select(@NonNull RemarkableClient client) {
		this.client = client;
	}

	public void selectClient(@NonNull String selector) {
		select(getClient(selector));
	}

	public void deselect(@NonNull RemarkableClient client) {
		if (client.equals(this.client)) {
			deselectSelectedClient();
		}
	}

	public void deselectSelectedClient() {
		this.client = null;
	}

	public RemarkableClient getSelectedClient() {
		return Optional.ofNullable(client).orElseThrow(() -> new RuntimeException("No client selected."));
	}

	public RemarkableClient getClient(String optionalSelector) {
		return optionalSelector != null ? clientManager.getClient(optionalSelector.trim()) : getSelectedClient();
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
		return withStyle(BOLD, (client != null ? client.getDescription() : "") + "> ");
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
