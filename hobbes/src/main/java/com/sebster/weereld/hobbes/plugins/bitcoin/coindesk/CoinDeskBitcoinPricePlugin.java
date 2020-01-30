package com.sebster.weereld.hobbes.plugins.bitcoin.coindesk;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Collections.singletonList;
import static java.util.regex.Pattern.compile;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import lombok.NonNull;

@Component
public class CoinDeskBitcoinPricePlugin extends BasePlugin {

	private static final URI COINDESK_BPI_URI = URI.create("http://api.coindesk.com/v1/bpi/currentprice.json");
	private static final Pattern COINDESK_PRICE_PATTERN = compile("(?i)([0-9]+(?:\\.?[0-9]+)?) bitcoin(?: in ([a-z]+))?");
	private static final String DEFAULT_CURRENCY_CODE = "EUR";

	private final @NonNull RestTemplate restTemplate;

	public CoinDeskBitcoinPricePlugin(@NonNull RestTemplateBuilder restTemplateBuilder) {
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		messageConverter.setSupportedMediaTypes(singletonList(new MediaType("application", "javascript")));
		restTemplate = restTemplateBuilder.messageConverters(messageConverter).build();
	}

	@Override
	public String getName() {
		return "coindesk";
	}

	@Override
	public String getDescription() {
		return "Vraag naar de bitcoin prijs via de CoinDesk bitcoin prijs index.";
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		String text = message.getText().trim();

		Matcher matcher = COINDESK_PRICE_PATTERN.matcher(text);
		int start = 0;
		while (matcher.find(start)) {
			BigDecimal amount = new BigDecimal(matcher.group(1));
			String code = matcher.group(2) != null ? matcher.group(2).toUpperCase() : DEFAULT_CURRENCY_CODE;
			showCoinDeskPrice(message.getChat(), code, amount);
			start = matcher.end();
		}
	}

	private void showCoinDeskPrice(TelegramChat chat, String code, BigDecimal amount) {
		Optional<BigDecimal> price = getCoinDeskPrice(code, amount);
		if (price.isPresent()) {
			sendMessage(chat, "%s bitcoin is %s %s.", amount, price.get().setScale(2, HALF_UP), code);
		} else {
			sendMessage(chat, "Ik weet even niet hoeveel %s bitcoin is in %s.", amount, code);
		}
	}

	private Optional<BigDecimal> getCoinDeskPrice(String code, BigDecimal amount) {
		try {
			CoinDeskBitcoinPriceIndex bpi = restTemplate.getForObject(COINDESK_BPI_URI, CoinDeskBitcoinPriceIndex.class);
			return bpi.getPrice(code).map(price -> price.getRate().multiply(amount));
		} catch (RuntimeException e) {
			logger.warn("Error fetching bitcoin price", e);
			return Optional.empty();
		}
	}

}
