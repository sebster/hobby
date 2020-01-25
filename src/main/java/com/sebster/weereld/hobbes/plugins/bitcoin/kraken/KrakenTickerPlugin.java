package com.sebster.weereld.hobbes.plugins.bitcoin.kraken;

import static java.util.Arrays.asList;
import static java.util.regex.Pattern.compile;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;

@Component
public class KrakenTickerPlugin extends BasePlugin {

	private static final URI TICKER_URI = URI.create("https://api.kraken.com/0/public/Ticker");
	private static final Pattern PATTERN = compile("(?i)([0-9]+(?:\\.?[0-9]+)?) kraken ([A-Z]{3,4})(?: in ([A-Z]{3,4}))?(:\\b|$)");
	private static final String DEFAULT_CURRENCY_CODE = "EUR";

	private final RestTemplate restTemplate;

	@Autowired
	public KrakenTickerPlugin(RestTemplateBuilder builder) {
		restTemplate = builder.build();
	}

	@Override
	public void visitTextMessage(TelegramTextMessage textMessage) {
		String text = textMessage.getText().trim();

		Matcher matcher = PATTERN.matcher(text);
		int start = 0;
		while (matcher.find(start)) {
			BigDecimal amount = new BigDecimal(matcher.group(1));
			String baseAsset = matcher.group(2).toUpperCase();
			String quoteAsset = matcher.group(3) != null ? matcher.group(3).toUpperCase() : DEFAULT_CURRENCY_CODE;
			showKrakenRate(textMessage.getChat(), baseAsset, quoteAsset, amount);
			start = matcher.end();
		}
	}

	private void showKrakenRate(TelegramChat chat, String baseAsset, String quoteAsset, BigDecimal amount) {
		Optional<BigDecimal> price = getKrakenPrice(baseAsset, quoteAsset, amount);
		if (price.isPresent()) {
			sendMessage(chat, "%s %s is %s %s.", amount, baseAsset, price.get(), quoteAsset);
		} else {
			sendMessage(chat, "Ik weet even niet hoeveel %s %s is in %s.", amount, baseAsset, quoteAsset);
		}
	}

	private Optional<BigDecimal> getKrakenPrice(String baseAsset, String quoteAsset, BigDecimal amount) {
		try {
			String krakenPair = krakenPair(baseAsset, quoteAsset);
			KrakenTickerResponse response = restTemplate.getForObject(
					UriComponentsBuilder.fromUri(TICKER_URI).queryParam("pair", krakenPair).toUriString(),
					KrakenTickerResponse.class
			);
			BigDecimal value = response.getResult().values().iterator().next().getLastPrice().multiply(amount);
			return Optional.of(value);
		} catch (RuntimeException e) {
			logger.warn("Error fetching bitcoin price", e);
			return Optional.empty();
		}
	}

	private String krakenPair(String baseAsset, String quoteAsset) {
		baseAsset = krakenNormalize(baseAsset);
		quoteAsset = krakenNormalize(quoteAsset);
		if (asList("BCH", "DASH", "EOS", "GNO").contains(baseAsset)) {
			return baseAsset + quoteAsset;
		}
		if ("USDT".equals(baseAsset)) {
			return baseAsset + addKrakenPrefix(quoteAsset);
		}
		return addKrakenPrefix(baseAsset) + addKrakenPrefix(quoteAsset);
	}

	private String krakenNormalize(String asset) {
		if ("BTC".equals(asset)) {
			return "XBT";
		}
		return asset;
	}

	private String addKrakenPrefix(String asset) {
		if (asList("EUR", "USD", "GBP", "CAD", "JPY").contains(asset)) {
			return "Z" + asset;
		} else if (asset.length() == 3) {
			return "X" + asset;
		} else {
			return asset;
		}
	}

}
