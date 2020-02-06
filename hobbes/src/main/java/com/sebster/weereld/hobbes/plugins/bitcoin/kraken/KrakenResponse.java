package com.sebster.weereld.hobbes.plugins.bitcoin.kraken;

import static java.util.Collections.emptyList;
import static java.util.List.copyOf;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = PRIVATE)
public abstract class KrakenResponse<T> {

	@NonNull List<String> errors;
	T result;

	public KrakenResponse(List<String> errors, T result) {
		this.errors = errors == null ? emptyList() : copyOf(errors);
		this.result = result;
	}

}
