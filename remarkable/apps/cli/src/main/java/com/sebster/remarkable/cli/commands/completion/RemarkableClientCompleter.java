package com.sebster.remarkable.cli.commands.completion;

import static com.sebster.commons.strings.Strings.startsWithIgnoreCase;

import java.util.List;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RemarkableClientCompleter implements Completer {

	private final @NonNull CompletionContext completionContext;

	@Override
	public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
		String start = line.word().substring(0, line.wordCursor());
		completionContext.getClientManager().listClients().forEach(client -> {
			if (startsWithIgnoreCase(client.getDescription(), start)) {
				candidates.add(new Candidate(client.getDescription()));
			}
		});
	}

}
