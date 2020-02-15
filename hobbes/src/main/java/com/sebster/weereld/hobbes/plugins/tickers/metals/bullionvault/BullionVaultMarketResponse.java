package com.sebster.weereld.hobbes.plugins.tickers.metals.bullionvault;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
class BullionVaultMarketResponse {

	BullionVaultMarket market;

	Optional<BullionVaultMarket> getMarket() {
		return Optional.ofNullable(market);
	}

	Optional<BigDecimal> getBestSellPrice(@NonNull String metal, @NonNull String currencyCode) {
		return getMarket().flatMap(market -> market.getBestSellPrice(metal, currencyCode));
	}

}
