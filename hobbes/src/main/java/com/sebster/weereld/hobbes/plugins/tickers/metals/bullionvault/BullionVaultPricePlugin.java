package com.sebster.weereld.hobbes.plugins.tickers.metals.bullionvault;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;
import static java.util.regex.Pattern.compile;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import lombok.NonNull;

@Component
public class BullionVaultPricePlugin extends BasePlugin {

	private static final URI BULLIONVAULT_MARKETS_URI =
			URI.create("https://www.bullionvault.com/view_market_json.do?marketWidth=1");
	private static final Pattern BULLIONVAULT_PRICE_PATTERN = compile(
			"(?i)(?:^|\\B)([0-9]*(?:\\.?[0-9]+)?) (gram|kg|staaf|staven) (goud|zilver|platina)(?: in ([a-z]+))?");
	private static final String DEFAULT_CURRENCY_CODE = "EUR";

	private static final BigDecimal GRAM_IN_KILOGRAMS = new BigDecimal("1e-3");
	private static final BigDecimal TROY_OUNCE_IN_GRAMS = new BigDecimal("31.1034768");
	private static final BigDecimal BAR_IN_TROY_OUNCES = new BigDecimal("400");

	private final @NonNull RestTemplate restTemplate;

	public BullionVaultPricePlugin(@NonNull RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	@Override
	public String getName() {
		return "bullionvault";
	}

	@Override
	public String getDescription() {
		return "Vraag naar de goud prijs bij BullionVault.";
	}

	@Override
	public void showHelp(TelegramChat chat) {
		sendMessage(chat, "<aantal> (kg|gram|staaf|staven) (goud|zilver|platina) [in <munt>] - haal de BullionVault prijs goud op");
		sendMessage(chat, "voorbeeld: 2 staven goud in usd");
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		String text = message.getText().trim();

		Matcher matcher = BULLIONVAULT_PRICE_PATTERN.matcher(text);
		int start = 0;
		while (matcher.find(start)) {
			BigDecimal amount = new BigDecimal(matcher.group(1));
			String weightUnit = matcher.group(2);
			String metal = matcher.group(3);
			String currencyCode = matcher.group(4) != null ? matcher.group(4).toUpperCase() : DEFAULT_CURRENCY_CODE;
			showMetalPrice(message.getChat(), metal, weightUnit, currencyCode, amount);
			start = matcher.end();
		}
	}

	private void showMetalPrice(TelegramChat chat, String metal, String weightUnit, String currencyCode, BigDecimal amount) {
		BigDecimal weightKg = getWeightInKilograms(weightUnit);
		Optional<BigDecimal> priceKg = getKgMetalPrice(getMetalName(metal), currencyCode);
		if (priceKg.isPresent()) {
			BigDecimal price = priceKg.get().multiply(weightKg).multiply(amount);
			sendMessage(chat, "%s %s %s is %s %s.", amount, weightUnit, metal, price.setScale(2, HALF_UP), currencyCode);
		} else {
			sendMessage(chat, "Ik weet even niet hoeveel %s %s %s is in %s.", amount, weightUnit, metal, currencyCode);
		}
	}

	private Optional<BigDecimal> getKgMetalPrice(String metal, String currencyCode) {
		try {
			return Optional.ofNullable(restTemplate.getForObject(BULLIONVAULT_MARKETS_URI, MarketResponse.class))
					.flatMap(response -> response.getSellPrice(metal, currencyCode));
		} catch (RuntimeException e) {
			logger.warn("Error fetching bitcoin price", e);
			return Optional.empty();
		}
	}

	private String getMetalName(String metal) {
		switch (metal.toLowerCase()) {
		case "goud":
			return "GOLD";
		case "zilver":
			return "SILVER";
		case "platina":
			return "PLATINUM";
		}
		throw new IllegalArgumentException("Invalid metal: " + metal);
	}

	private BigDecimal getWeightInKilograms(String weightUnit) {
		switch (weightUnit.toLowerCase()) {
		case "kg":
			return ONE;
		case "gram":
			return GRAM_IN_KILOGRAMS;
		case "staaf":
		case "staven":
			return BAR_IN_TROY_OUNCES.multiply(TROY_OUNCE_IN_GRAMS).multiply(GRAM_IN_KILOGRAMS);
		}
		throw new IllegalArgumentException("Invalid weight unit: " + weightUnit);
	}

}
