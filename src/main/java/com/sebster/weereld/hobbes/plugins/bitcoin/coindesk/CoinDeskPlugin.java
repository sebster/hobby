package com.sebster.weereld.hobbes.plugins.bitcoin.coindesk;

import static java.math.RoundingMode.HALF_UP;
import static java.util.regex.Pattern.compile;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;

@Component
public class CoinDeskPlugin extends BasePlugin {

	private static final URI COINDESK_BPI_URI = URI.create("http://api.coindesk.com/v1/bpi/currentprice.json");
	private static final String DEFAULT_CURRENCY_CODE = "EUR";
	private static final Pattern BITCOIN_PRICE_PATTERN = compile("(?i)([0-9]+(?:\\.?[0-9]+)?) bitcoin(?: in ([a-z]+))?");

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void visitTextMessage(TelegramTextMessage textMessage) {
		String text = textMessage.getText().trim();

		Matcher matcher = BITCOIN_PRICE_PATTERN.matcher(text);
		int start = 0;
		while (matcher.find(start)) {
			BigDecimal amount = new BigDecimal(matcher.group(1));
			String code = matcher.group(2) != null ? matcher.group(2) : DEFAULT_CURRENCY_CODE;
			showBitcoinRate(textMessage.getChat(), code, amount);
			start = matcher.end();
		}
	}

	private void showBitcoinRate(TelegramChat chat, String code, BigDecimal amount) {
		Optional<BigDecimal> price = getBitcoinPrice(code, amount);
		if (price.isPresent()) {
			sendMessage(chat, "%s bitcoin is %s %s.", amount, price.get().setScale(2, HALF_UP), code.toUpperCase());
		} else {
			sendMessage(chat, "Ik weet even niet hoeveel %s bitcoin is in %s.", amount, code.toUpperCase());
		}
	}

	private Optional<BigDecimal> getBitcoinPrice(String code, BigDecimal amount) {
		try {
			CoinDeskBitcoinPriceIndex bpi = restTemplate.getForObject(COINDESK_BPI_URI, CoinDeskBitcoinPriceIndex.class);
			return bpi.getPrice(code).map(price -> price.rate().multiply(amount));
		} catch (RuntimeException e) {
			logger.warn("Error fetching bitcoin price", e);
			return Optional.empty();
		}
	}

}
