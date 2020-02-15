package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.coindesk;

import static java.util.Collections.emptyMap;
import static lombok.AccessLevel.PRIVATE;

import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
class CoinDeskBitcoinPriceIndex {

	Map<String, CoinDeskBitcoinPrice> bpi;

	Optional<CoinDeskBitcoinPrice> getPrice(@NonNull String code) {
		return Optional.ofNullable(getBpi().get(code));
	}

	Map<String, CoinDeskBitcoinPrice> getBpi() {
		return Optional.ofNullable(bpi).orElse(emptyMap());
	}

}
