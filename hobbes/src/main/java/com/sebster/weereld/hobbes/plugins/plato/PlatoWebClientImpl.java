package com.sebster.weereld.hobbes.plugins.plato;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PlatoWebClientImpl implements PlatoWebClient {

	private static final URI PLATO_RANDOM_ENTRY_URL = URI.create("https://plato.stanford.edu/cgi-bin/encyclopedia/random");

	public Optional<String> getRandomEntry() {
		try {
			return Optional.of(StringEscapeUtils.unescapeHtml4(IOUtils.toString(PLATO_RANDOM_ENTRY_URL, UTF_8)));
		} catch (IOException e) {
			log.warn("Error fetching random entry from plato", e);
			return Optional.empty();
		}
	}

}
