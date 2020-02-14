package com.sebster.weereld.hobbes.plugins.plato;

import static com.sebster.weereld.hobbes.plugins.plato.PlatoSchedule.platoSchedule;
import static com.sebster.weereld.hobbes.plugins.plato.PlatoSubscription.platoSubscription;
import static com.sebster.weereld.hobbes.plugins.plato.PlatoSubscriptionSpecification.withChatId;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.regex.Pattern.compile;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.Repository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlatoService {

	private static final String PLATO_ENTRY_BASE_URL_STRING = "https://plato.stanford.edu/entries/";
	private static final URI PLATO_RANDOM_ENTRY_URL = URI.create("https://plato.stanford.edu/cgi-bin/encyclopedia/random");

	private static final Pattern CITATION_PATTERN = compile("(?i)<div id=\"preamble\">\\n\\n<p>.*\\n([^.]*.[^.]*.)");
	private static final Pattern ENTRY_PATTERN = compile(
			"(?i) {12}<li><a href=\"https://plato.stanford.edu/cgi-bin/encyclopedia/archinfo\\.cgi\\?entry=([\\-\\w]*)");

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Repository<PlatoSubscription> repository;
	private final PlatoProperties properties;
	private final PlatoScheduler scheduler;

	String getQuoteFromRandomEntry() {
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

	void subscribe(long chatId) {
		subscribe(chatId, properties.getUnsolicitedQuotesIntervalLowerBound(), properties.getUnsolicitedQuotesIntervalUpperBound());
	}

	private void subscribe(long chatId, Duration intervalLowerBound, Duration intervalUpperBound) {
		Optional<PlatoSubscription> subscription = repository.findOne(withChatId(chatId));

		if (subscription.isPresent()) {
			subscription.get()
					.setSchedule(platoSchedule(intervalLowerBound.toMillis(), intervalLowerBound.toMillis()));

			scheduler.updateScheduleFor(subscription.get());
			logger.debug("Updated subscription for {}", chatId);
		} else {
			PlatoSubscription newSubscription = platoSubscription(chatId,
					platoSchedule(intervalLowerBound.toMillis(), intervalUpperBound.toMillis()));
			repository.add(newSubscription);
			scheduler.addScheduleFor(newSubscription);
			logger.debug("Added subscription for {}", chatId);
		}
	}

	void unsubscribe(long chatId) {
		repository.removeAll(withChatId(chatId));
		scheduler.cancelScheduleFor(chatId);
		logger.debug("Cancelled subscription for {}", chatId);
	}

	private Optional<String> getRandomEntry() {
		try {
			return Optional.of(StringEscapeUtils.unescapeHtml4(IOUtils.toString(PLATO_RANDOM_ENTRY_URL, UTF_8)));
		} catch (IOException e) {
			logger.warn("Error fetching random entry from plato", e);
			return Optional.empty();
		}
	}

	private Optional<String> citation(String entry) {
		return firstMatch(entry, CITATION_PATTERN)
				.map(citation -> citation
						.replace(" \n", " ")
						.replace("\n ", " ")
						.replace("\n", " ")
						.replaceAll("<[^>]*>", "")
				);
	}

	private Optional<String> entryLink(String entry) {
		return firstMatch(entry, ENTRY_PATTERN)
				.map(name -> PLATO_ENTRY_BASE_URL_STRING + name);
	}

	private Optional<String> firstMatch(String string, Pattern pattern) {
		Matcher matcher = pattern.matcher(string);
		return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
	}

}
