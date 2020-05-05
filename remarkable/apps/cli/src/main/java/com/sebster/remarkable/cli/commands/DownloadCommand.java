package com.sebster.remarkable.cli.commands;

import static com.sebster.commons.uuids.Uuids.isUuid;
import static com.sebster.remarkable.cloudapi.RemarkablePath.parsePath;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClient;
import com.sebster.remarkable.cloudapi.RemarkableItem;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "get", description = "Download a document or folder")
public class DownloadCommand extends BaseCommand {

	@Parameters(
			paramLabel = "item",
			description = "The document(s) or folder(s) to download.",
			arity = "1..*"
	)
	private List<String> items;

	@Override
	public void run() {
		items.forEach(file -> download(cli.getSelectedClient(), file));
	}

	private void download(RemarkableClient client, String file) {
		try {
			RemarkableItem item;
			String itemDisplay;
			if (!isUuid(file)) {
				item = client.list().getItem(parsePath(file));
				itemDisplay = cli.withItemStyle(item);
			} else {
				item = client.list().getItem(UUID.fromString(file));
				itemDisplay = cli.withItemIdStyle(item.getId());
			}
			File target = new File(item.getId() + ".zip");
			cli.printf("Downloading %s to %s...\n", itemDisplay, cli.withFileStyle(target));
			client.download(item, data -> copyInputStreamToFile(data, target));
		} catch (Exception e) {
			cli.println(cli.withErrorStyle(e.getMessage()));
		}
	}

}
