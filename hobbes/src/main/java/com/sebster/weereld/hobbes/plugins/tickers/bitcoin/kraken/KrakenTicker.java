package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.kraken;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class KrakenTicker {

	BigDecimal askPrice, askLotVolume, bidPrice, bidLotVolume, lastPrice, lastLotVolume;
	BigDecimal volumeToday, volume24Hours, vwapToday, vwap24Hours;
	Long tradesToday, trades24Hours;
	BigDecimal lowToday, low24Hours, highToday, high24Hours, openToday;

	@JsonCreator
	public KrakenTicker(
			@JsonProperty("a") BigDecimal[] ask, @JsonProperty("b") BigDecimal[] bid, @JsonProperty("c") BigDecimal[] last,
			@JsonProperty("v") BigDecimal[] volume, @JsonProperty("p") BigDecimal[] vwap,
			@JsonProperty("t") Long[] trades,
			@JsonProperty("l") BigDecimal[] low, @JsonProperty("h") BigDecimal[] high, @JsonProperty("o") BigDecimal open
	) {
		askPrice = ask[0];
		askLotVolume = ask[2];
		bidPrice = bid[0];
		bidLotVolume = bid[2];
		lastPrice = last[0];
		lastLotVolume = last[1];

		volumeToday = volume[0];
		volume24Hours = volume[1];
		vwapToday = vwap[0];
		vwap24Hours = vwap[1];

		tradesToday = trades[0];
		trades24Hours = trades[1];

		lowToday = low[0];
		low24Hours = low[1];
		highToday = high[0];
		high24Hours = high[1];
		openToday = open;
	}

}
