package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.coindesk;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

@Value
public class CoinDeskBitcoinPrice {

	@NonNull String code;

	@JsonProperty("rate_float")
	@NonNull BigDecimal rate;

	@JsonCreator
	public CoinDeskBitcoinPrice(@JsonProperty("code") String code, @JsonProperty("rate_float") BigDecimal rate) {
		this.code = code;
		this.rate = rate;
	}

}
