package com.sebster.remarkable.cli.commands;

import static java.time.ZoneId.systemDefault;

import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import com.sebster.remarkable.cloudapi.RemarkableCollection;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "list",
		aliases = "ls",
		mixinStandardHelpOptions = true,
		description = "List the contents of the reMarkable",
		version = "1.0"
)
public class ListCommand implements Runnable {

	private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(systemDefault());

	@ParentCommand
	private Cli cli;

	@Option(names = { "-l", "--long" }, description = "Long format: id, version, modification time, item.")
	private boolean longFormat;

	@Option(names = { "-R", "--recursive" }, description = "Recurse into subfolders.")
	private boolean recursive;

	@Override
	public void run() {
		cli.doWithClient(client -> printCollection(client.list()));
	}

	private void printCollection(RemarkableCollection collection) {
		Stream<RemarkableItem> items = recursive ? collection.recurse() : collection.stream();
		items.forEach(this::printItem);
	}

	private void printItem(RemarkableItem item) {
		if (longFormat) {
			cli.printf("%s    %4d    %s     %s\n",
					cli.withItemIdStyle(item.getId()),
					item.getVersion(),
					DATE_TIME_FORMAT.format(item.getModificationTime()),
					cli.withItemStyle(item)
			);
		} else {
			cli.println(cli.withItemStyle(item));
		}
	}

}