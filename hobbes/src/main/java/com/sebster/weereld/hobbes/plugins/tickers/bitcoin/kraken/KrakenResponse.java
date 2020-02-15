package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.kraken;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public abstract class KrakenResponse<T> {

	List<String> errors;
	T result;

}
