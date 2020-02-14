package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.coindesk;

import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Collections.unmodifiableMap;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

@Value
public class CoinDeskBitcoinPriceIndex {

	@JsonProperty("bpi")
	@NonNull Map<String, CoinDeskBitcoinPrice> priceMap;

	@JsonCreator
	public CoinDeskBitcoinPriceIndex(@JsonProperty("bpi") Map<String, CoinDeskBitcoinPrice> priceMap) {
		Map<String, CoinDeskBitcoinPrice> copy = new TreeMap<>(CASE_INSENSITIVE_ORDER);
		copy.putAll(priceMap);
		this.priceMap = unmodifiableMap(copy);
	}

	public Optional<CoinDeskBitcoinPrice> getPrice(@NonNull String code) {
		return Optional.ofNullable(priceMap.get(code));
	}

}
