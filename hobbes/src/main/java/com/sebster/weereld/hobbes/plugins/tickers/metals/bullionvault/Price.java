package com.sebster.weereld.hobbes.plugins.tickers.metals.bullionvault;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Price {

	BigDecimal limit;

	public Optional<BigDecimal> getLimit() {
		return Optional.ofNullable(limit);
	}

}
