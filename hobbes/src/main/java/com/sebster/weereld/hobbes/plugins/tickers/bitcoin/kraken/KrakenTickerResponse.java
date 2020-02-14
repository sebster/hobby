package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.kraken;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString(callSuper = true)
public class KrakenTickerResponse extends KrakenResponse<Map<String, KrakenTicker>> {

	@JsonCreator
	public KrakenTickerResponse(
			@JsonProperty("error") List<String> errors,
			@JsonProperty("result") Map<String, KrakenTicker> result
	) {
		super(errors, result);
	}

}
