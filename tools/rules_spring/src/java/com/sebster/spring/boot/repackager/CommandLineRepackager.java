package com.sebster.spring.boot.repackager;

import static org.springframework.boot.loader.tools.LibraryScope.RUNTIME;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.HelpCommand;
import static picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.loader.tools.Libraries;
import org.springframework.boot.loader.tools.Library;
import org.springframework.boot.loader.tools.Repackager;

import picocli.CommandLine;

@Command(name = "repackager", mixinStandardHelpOptions = true, subcommands = { HelpCommand.class })
public class CommandLineRepackager implements Runnable {

	@Option(names = { "-f", "--source-jar" }, required = true)
	private File sourceJar;

	@Option(names = { "-o", "--output" }, required = true)
	private File output;

	@Option(names = { "-r", "--runtime-dependencies" }, split = ",", required = true)
	private List<File> dependencies = new ArrayList<>();

	@Option(names = { "-m", "--main-class" }, required = true)
	private String mainClass;

	@Override
	public void run() {
		try {
			Repackager springRepackager = new Repackager(sourceJar);
			springRepackager.setMainClass(mainClass);
			springRepackager.repackage(output, libraries(dependencies));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static Libraries libraries(List<File> dependencies) {
		return callback -> {
			for (int i = 0; i < dependencies.size(); i++) {
				File dependency = dependencies.get(i);
				callback.library(new Library(i + "_" + dependency.getName(), dependency, RUNTIME, false));
			}
		};
	}

	public static void main(String[] args) {
		new CommandLine(new CommandLineRepackager()).execute(args);
	}

}
