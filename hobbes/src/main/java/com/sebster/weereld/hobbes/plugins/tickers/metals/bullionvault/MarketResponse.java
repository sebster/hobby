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
public class MarketResponse {

	@NonNull Market market;

	public Optional<BigDecimal> getSellPrice(@NonNull String metal, @NonNull String currencyCode) {
		return market != null ? market.getSellPrice(metal, currencyCode) : Optional.empty();
	}

}
