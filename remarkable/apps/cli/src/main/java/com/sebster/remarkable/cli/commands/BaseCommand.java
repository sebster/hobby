package com.sebster.remarkable.cli.commands;

import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

public abstract class BaseCommand implements Runnable {

	@ParentCommand
	protected Cli cli;

	@Option(names = { "-h", "--help" }, usageHelp = true, description = "Show this help message.")
	private boolean usage;

}
