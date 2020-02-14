package com.sebster.weereld.hobbes.plugins.tickers.metals.bullionvault;

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
public class Pitch {

	String securityClassNarrative;
	String considerationCurrency;
	List<Price> buyPrices;
	List<Price> sellPrices;

	public boolean isForMetal(@NonNull String security) {
		return Objects.equals(securityClassNarrative, security);
	}

	public boolean isForCurrencyCode(@NonNull String currencyCode) {
		return Objects.equals(considerationCurrency, currencyCode);
	}

	public Optional<BigDecimal> getSellPrice() {
		return sellPrices != null ? sellPrices.stream().findFirst().flatMap(Price::getLimit) : Optional.empty();
	}

}
