package com.sebster.weereld.hobbes.plugins.plato;

import static java.util.regex.Pattern.compile;

import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class PlatoEntryService {

	private static final String PLATO_ENTRY_BASE_URL_STRING = "https://plato.stanford.edu/entries/";

	private static final Pattern CITATION_PATTERN = compile("(?i)<div id=\"preamble\">\\n\\n<p>.*\\n([^.]*.[^.]*.)");
	private static final Pattern ENTRY_NAME_PATTERN = compile(
			"(?i) {12}<li><a href=\"https://plato.stanford.edu/cgi-bin/encyclopedia/archinfo\\.cgi\\?entry=([\\-\\w]*)");

	private final PlatoWebClient webClient;

	String getCitationFromRandomEntry() {
		Optional<String> randomEntry = webClient.getRandomEntry();

		if (randomEntry.isPresent()) {
			Optional<String> quote = quote(randomEntry.get());
			Optional<String> link = entryLink(randomEntry.get());

			if (link.isPresent() || quote.isPresent()) {
				return String.join("\n", quote.orElse(""), link.orElse(""));
			}
		}

		return "Ik sta met m'n mond vol tanden...";
	}

	private Optional<String> quote(String entry) {
		return firstMatch(entry, CITATION_PATTERN)
				.map(citation -> citation
						.replace(" \n", " ")
						.replace("\n ", " ")
						.replace("\n", " ")
						.replaceAll("<[^>]*>", "")
				);
	}

	private Optional<String> entryLink(String entry) {
		return firstMatch(entry, ENTRY_NAME_PATTERN)
				.map(name -> PLATO_ENTRY_BASE_URL_STRING + name);
	}

	private Optional<String> firstMatch(String string, Pattern pattern) {
		Matcher matcher = pattern.matcher(string);
		return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
	}

}
