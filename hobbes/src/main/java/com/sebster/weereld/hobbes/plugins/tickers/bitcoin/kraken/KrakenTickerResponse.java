package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.kraken;

import java.util.Map;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString(callSuper = true)
public class KrakenTickerResponse extends KrakenResponse<Map<String, KrakenTicker>> {
}
