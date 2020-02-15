package com.sebster.weereld.hobbes.plugins.tickers.metals.bullionvault;

import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
class BullionVaultMarket {

	List<BullionVaultPitch> pitches;

	List<BullionVaultPitch> getPitches() {
		return Optional.ofNullable(pitches).orElse(emptyList());
	}

	Optional<BigDecimal> getBestSellPrice(@NonNull String metal, @NonNull String currencyCode) {
		return getPitches().stream()
				.filter(pitch -> pitch.isForMetal(metal))
				.filter(pitch -> pitch.isForCurrencyCode(currencyCode))
				.map(BullionVaultPitch::getBestSellPrice)
				.flatMap(Optional::stream)
				.min(naturalOrder());
	}

}
