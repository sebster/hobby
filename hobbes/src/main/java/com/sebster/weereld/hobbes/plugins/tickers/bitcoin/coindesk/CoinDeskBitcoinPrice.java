package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.coindesk;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
class CoinDeskBitcoinPrice {

	String code;
	BigDecimal rateFloat;

}
