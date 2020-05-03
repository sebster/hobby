package com.sebster.remarkable.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "deselect", description = "Deselect the selected client")
public class DeselectCommand extends BaseCommand {

	@Override
	public void run() {
		cli.deselect(cli.getSelectedClient());
	}

}
