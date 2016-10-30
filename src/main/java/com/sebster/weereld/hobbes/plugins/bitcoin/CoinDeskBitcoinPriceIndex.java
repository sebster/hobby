package com.sebster.weereld.hobbes.plugins.bitcoin;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinDeskBitcoinPriceIndex {

	private final Map<String, CoinDeskBitcoinPrice> bpi;

	@JsonCreator
	public CoinDeskBitcoinPriceIndex(@JsonProperty("bpi") Map<String, CoinDeskBitcoinPrice> bpi) {
		this.bpi = new TreeMap<>(CASE_INSENSITIVE_ORDER);
		this.bpi.putAll(bpi);
	}

	public Optional<CoinDeskBitcoinPrice> getPrice(String code) {
		return Optional.ofNullable(bpi.get(code));
	}

}
