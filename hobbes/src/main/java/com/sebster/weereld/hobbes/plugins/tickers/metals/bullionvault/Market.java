package com.sebster.weereld.hobbes.plugins.tickers.metals.bullionvault;

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
public class Market {

	@NonNull List<Pitch> pitches;

	public Optional<BigDecimal> getSellPrice(String metal, String currencyCode) {
		return pitches.stream()
				.filter(pitch -> pitch.isForMetal(metal))
				.filter(pitch -> pitch.isForCurrencyCode(currencyCode))
				.findFirst()
				.flatMap(Pitch::getSellPrice);
	}

}
