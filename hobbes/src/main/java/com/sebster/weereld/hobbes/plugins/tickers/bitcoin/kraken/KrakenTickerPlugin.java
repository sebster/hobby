package com.sebster.weereld.hobbes.plugins.tickers.bitcoin.kraken;

import static java.util.Arrays.asList;
import static java.util.regex.Pattern.compile;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import lombok.NonNull;

@Component
public class KrakenTickerPlugin extends BasePlugin {

	private static final URI TICKER_URI = URI.create("https://api.kraken.com/0/public/Ticker");
	private static final Pattern PATTERN =
			compile("(?i)(?:^|\\b)([0-9]*(?:\\.?[0-9]+)?) kraken ([A-Z]{3,4})(?: in ([A-Z]{3,4}))?(?:\\b|$)");
	private static final String DEFAULT_CURRENCY_CODE = "EUR";

	private final @NonNull RestTemplate restTemplate;

	public KrakenTickerPlugin(@NonNull RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	@Override
	public String getName() {
		return "kraken";
	}

	@Override
	public String getDescription() {
		return "Vraag naar cryptomunt prijzen op de Kraken exchange.";
	}

	@Override
	public void showHelp(TelegramChat chat) {
		sendMessage(chat, "<aantal> kraken <munt1> [in <munt2>] - haal de kraken prijs op van <aantal> <munt1> in <munt2>");
		sendMessage(chat, "voorbeeld: 1.5 kraken btc in usd");
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		String text = message.getText().trim();

		Matcher matcher = PATTERN.matcher(text);
		int start = 0;
		while (matcher.find(start)) {
			BigDecimal amount = new BigDecimal(matcher.group(1));
			String baseAsset = matcher.group(2).toUpperCase();
			String quoteAsset = matcher.group(3) != null ? matcher.group(3).toUpperCase() : DEFAULT_CURRENCY_CODE;
			showKrakenRate(message.getChat(), baseAsset, quoteAsset, amount);
			start = matcher.end();
		}
	}

	private void showKrakenRate(TelegramChat chat, String baseAsset, String quoteAsset, BigDecimal amount) {
		Optional<BigDecimal> price = getKrakenPrice(baseAsset, quoteAsset, amount);
		if (price.isPresent()) {
			String priceString = price.get().stripTrailingZeros().toPlainString();
			sendMessage(chat, "%s %s is %s %s.", amount, baseAsset, priceString, quoteAsset);
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
