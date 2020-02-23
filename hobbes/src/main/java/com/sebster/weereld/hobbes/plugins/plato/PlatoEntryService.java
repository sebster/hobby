package com.sebster.weereld.hobbes.plugins.plato;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.regex.Pattern.compile;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlatoEntryService {

	private static final String PLATO_ENTRY_BASE_URL_STRING = "https://plato.stanford.edu/entries/";
	private static final URI PLATO_RANDOM_ENTRY_URL = URI.create("https://plato.stanford.edu/cgi-bin/encyclopedia/random");

	private static final Pattern CITATION_PATTERN = compile("(?i)<div id=\"preamble\">\\n\\n<p>.*\\n([^.]*.[^.]*.)");
	private static final Pattern ENTRY_PATTERN = compile(
			"(?i) {12}<li><a href=\"https://plato.stanford.edu/cgi-bin/encyclopedia/archinfo\\.cgi\\?entry=([\\-\\w]*)");

	static String getQuoteFromRandomEntry() {
		Optional<String> randomEntry = getRandomEntry();

		if (randomEntry.isPresent()) {
			Optional<String> citation = citation(randomEntry.get());
			Optional<String> link = entryLink(randomEntry.get());

			if (link.isPresent() || citation.isPresent()) {
				return String.join("\n", citation.orElse(""), link.orElse(""));
			}
		}

		return "Ik sta met m'n mond vol tanden...";
	}

	private static Optional<String> getRandomEntry() {
		try {
			return Optional.of(StringEscapeUtils.unescapeHtml4(IOUtils.toString(PLATO_RANDOM_ENTRY_URL, UTF_8)));
		} catch (IOException e) {
			log.warn("Error fetching random entry from plato", e);
			return Optional.empty();
		}
	}

	private static Optional<String> citation(String entry) {
		return firstMatch(entry, CITATION_PATTERN)
				.map(citation -> citation
						.replace(" \n", " ")
						.replace("\n ", " ")
						.replace("\n", " ")
						.replaceAll("<[^>]*>", "")
				);
	}

	private static Optional<String> entryLink(String entry) {
		return firstMatch(entry, ENTRY_PATTERN)
				.map(name -> PLATO_ENTRY_BASE_URL_STRING + name);
	}

	private static Optional<String> firstMatch(String string, Pattern pattern) {
		Matcher matcher = pattern.matcher(string);
		return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
	}

}
