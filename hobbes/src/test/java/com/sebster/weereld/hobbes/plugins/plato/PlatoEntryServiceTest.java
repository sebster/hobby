package com.sebster.weereld.hobbes.plugins.plato;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PlatoEntryServiceTest {

	private final PlatoProperties properties = new PlatoProperties();

	@Parameters
	public static List<String> entries() {
		return asList(
				"imagination",
				"impartiality",
				"plato"
		);
	}

	@Parameter
	public String entryName;

	@Test
	public void composed_citation_is_like_we_expect() {
		PlatoEntryService entryService = entryService(entryName + ".html");
		String citation = entryService.getCitationFromRandomEntry();
		assertThat(citation, is(resource(entryName + "-citation.txt")));
	}

	private String resource(String name) {
		try (InputStream in = getClass().getResourceAsStream(name)) {
			requireNonNull(in, () -> "Resource not found: " + name);
			return IOUtils.toString(in, UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private PlatoEntryService entryService(String resource) {
		return new PlatoEntryService(webClient(resource), properties);
	}

	private PlatoWebClient webClient(String resource) {
		return () -> Optional.of(resource(resource));
	}

}