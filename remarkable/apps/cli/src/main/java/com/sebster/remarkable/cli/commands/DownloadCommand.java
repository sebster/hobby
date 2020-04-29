package com.sebster.remarkable.cli.commands;

import static com.sebster.commons.uuids.Uuids.isUuid;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import com.sebster.remarkable.cloudapi.RemarkablePath;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
		name = "download",
		aliases = "get",
		mixinStandardHelpOptions = true,
		description = "Download a file or folder",
		version = "1.0"
)
public class DownloadCommand implements Runnable {

	@ParentCommand
	private Cli cli;

	@Parameters(
			paramLabel = "file",
			description = "The file or folder id or name.",
			arity = "1..*"
	)
	private List<String> files;

	@Override
	public void run() {
		cli.doWithClient(client -> files.forEach(file -> download(client, file)));
	}

	private void download(RemarkableClient client, String file) {
		try {
			RemarkableItem item;
			String itemDisplay;
			if (!isUuid(file)) {
				item = client.list(RemarkablePath.parse(file));
				itemDisplay = cli.withItemStyle(item);
			} else {
				item = client.list(UUID.fromString(file));
				itemDisplay = cli.withItemIdStyle(item.getId());
			}
			File target = new File(item.getId() + ".zip");
			cli.printf("Downloading %s to %s...\n", itemDisplay, cli.withFileStyle(target));
			copyInputStreamToFile(client.download(item.getDownloadLink().orElseThrow()), target);
		} catch (Exception e) {
			cli.println(cli.withErrorStyle(e.getMessage()));
		}
	}

}
