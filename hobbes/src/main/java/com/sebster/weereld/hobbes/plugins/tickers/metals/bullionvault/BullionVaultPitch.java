package com.sebster.weereld.hobbes.plugins.tickers.metals.bullionvault;

import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
class BullionVaultPitch {

	String securityClassNarrative;
	String considerationCurrency;
	List<BullionVaultPrice> sellPrices;

	List<BullionVaultPrice> getSellPrices() {
		return Optional.ofNullable(sellPrices).orElse(emptyList());
	}

	boolean isForMetal(@NonNull String security) {
		return Objects.equals(securityClassNarrative, security);
	}

	boolean isForCurrencyCode(@NonNull String currencyCode) {
		return Objects.equals(considerationCurrency, currencyCode);
	}

	Optional<BigDecimal> getBestSellPrice() {
		return getSellPrices().stream().map(BullionVaultPrice::getLimit).flatMap(Optional::stream).min(naturalOrder());
	}

}
