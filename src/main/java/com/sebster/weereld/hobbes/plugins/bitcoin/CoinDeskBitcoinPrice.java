package com.sebster.weereld.hobbes.plugins.bitcoin;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinDeskBitcoinPrice {

	private String code;
	private BigDecimal rate;

	@JsonCreator
	public CoinDeskBitcoinPrice(@JsonProperty("code") String code, @JsonProperty("rate") BigDecimal rate) {
		this.code = code;
		this.rate = rate;
	}

	public String code() {
		return code;
	}

	public BigDecimal rate() {
		return rate;
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
