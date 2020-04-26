package com.sebster.remarkable.cli.commands;

import static org.jline.utils.AttributedStyle.BOLD;
import static org.jline.utils.AttributedStyle.CYAN;
import static org.jline.utils.AttributedStyle.GREEN;
import static org.jline.utils.AttributedStyle.RED;

import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableClientManager;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
				ListCommand.class,
				MkdirCommand.class,
				DownloadCommand.class,
				HelpCommand.class,
		}
)
@RequiredArgsConstructor
@Getter
public class Cli implements Runnable {

	private final @NonNull Terminal terminal;
	private final @NonNull RemarkableClientManager clientManager;
	private RemarkableClient client;

	public Optional<RemarkableClient> getClient() {
		return Optional.ofNullable(client);
	}

	public void run() {
		println(new CommandLine(this).getUsageMessage());
	}

	public void selectClient(@NonNull RemarkableClient client) {
		this.client = client;
	}

	public void deselectClient() {
		this.client = null;
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
		return "Error: " + message;
	}

	public void doWithClient(@NonNull Consumer<RemarkableClient> action) {
		getClient().ifPresentOrElse(action, () -> println(withErrorStyle("No client selected.")));
	}

	public void doWithClient(@NonNull String selector, @NonNull Consumer<RemarkableClient> action) {
		clientManager.findClient(selector).ifPresentOrElse(action, () -> println(withErrorStyle("Unknown client: " + selector)));
	}

}
