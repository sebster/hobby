package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.coindesk;

import static lombok.AccessLevel.PRIVATE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CoinDeskBitcoinPriceIndex {

	Map<String, CoinDeskBitcoinPrice> bpi = new HashMap<>();

	public Optional<CoinDeskBitcoinPrice> getPrice(@NonNull String code) {
		return Optional.ofNullable(bpi.get(code));
	}

}
