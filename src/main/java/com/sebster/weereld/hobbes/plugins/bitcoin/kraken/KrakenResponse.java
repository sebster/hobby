package com.sebster.weereld.hobbes.plugins.bitcoin.kraken;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

import java.util.List;

public class KrakenResponse<T> {

	private final List<String> errors;
	private final T result;

	public KrakenResponse(List<String> errors, T result) {
		this.errors = errors == null ? emptyList() : unmodifiableList(errors);
		this.result = result;
	}

	public boolean hasError() {
		return errors != null && !errors.isEmpty();
	}

	public List<String> getErrors() {
		return errors;
	}

	public T getResult() {
		return result;
	}

}
