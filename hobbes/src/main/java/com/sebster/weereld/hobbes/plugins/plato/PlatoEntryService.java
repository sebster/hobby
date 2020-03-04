package com.sebster.weereld.hobbes.plugins.plato;

import static java.util.regex.Pattern.compile;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

	private static final Pattern PREAMBLE_PATTERN = compile("(?i)<div id=\"preamble\">(.+?)</div>");
	private static final Pattern ENTRY_NAME_PATTERN = compile(
			"(?i)<li><a href=\"https://plato.stanford.edu/cgi-bin/encyclopedia/archinfo\\.cgi\\?entry=([\\-\\w]*)");

	private final PlatoWebClient webClient;
	private final PlatoProperties properties;

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
		return firstMatch(stripNewLines(entry), PREAMBLE_PATTERN)
				.map(this::cleanupHtmlTagsAndWhiteSpace)
				.map(this::composeQuote);
	}

	private String composeQuote(String preamble) {
		List<String> sentences = getSentences(preamble);
		StringBuilder quote = new StringBuilder();

		int target = properties.getQuoteCharactersTarget();
		for (String sentence : sentences) {
			if (Math.abs(target - quote.length()) < Math.abs(target - (quote.length() + sentence.length()))) {
				break;
			}
			quote.append(sentence);
		}

		return quote.toString().trim();
	}

	private String stripNewLines(String string) {
		return string
				.replace(" \n", " ")
				.replace("\n ", " ")
				.replace("\n", " ");
	}

	private String cleanupHtmlTagsAndWhiteSpace(String string) {
		return string
				.replaceAll("<[^>]*>", "")
				.replace("\t", "")
				.trim();
	}

	private List<String> getSentences(String text) {
		List<String> sentences = new ArrayList<>();
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		iterator.setText(text);

		int start = iterator.first();
		for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
			sentences.add(text.substring(start, end));
		}
		return sentences;
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
