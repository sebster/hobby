package com.sebster.weereld.hobbes.plugins.tickers.rates.wuvwav;

import static java.math.RoundingMode.HALF_UP;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.regex.Pattern.compile;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@EnableConfigurationProperties(WuvWavProperties.class)
@Slf4j
public class WuvWavPlugin extends BasePlugin {

	private static final URI EURIBOR_URI = URI.create("https://euribor-rates.eu");
	private static final Pattern EURIBOR_PATTERN =
			compile("(?i)<tr><td><a href=\"/en/current-euribor-rates/1/euribor-rate-1-month/\".*>(-?\\d+\\.\\d+) %</td></tr>");
	private static final Pattern WUV_WAV_EURIBOR_PATTERN = compile("(?i)(?:^|\\b)1 (wuv|wav|euribor)(?:\\b|$)");

	private final @NonNull WuvWavProperties properties;

	@Override
	public String getName() {
		return "wuvwav";
	}

	@Override
	public String getDescription() {
		return "Hypotheekrentetracker voor Sebster en Riep.";
	}

	@Override
	public void showHelp(TelegramChat chat) {
		sendMessage(chat, "1 wuv|wav|euribor - haal de wuv, wav, of euribor koers op");
		sendMessage(chat, "voorbeeld: 1 wav");
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		String text = message.getText().trim();

		Matcher matcher = WUV_WAV_EURIBOR_PATTERN.matcher(text);
		int start = 0;
		while (matcher.find(start)) {
			String rate = matcher.group(1);
			showRate(message.getChat(), rate);
			start = matcher.end();
		}
	}

	public void showRate(TelegramChat chat, String rate) {
		Optional<BigDecimal> euriborRate = lookUpEuriborRate();
		if (euriborRate.isEmpty()) {
			sendMessage(chat, "Ik weit ivin niet oiveil %s ies.", rate.replaceAll("u", "oe").replaceAll("i", "ie"));
			return;
		}

		switch (rate) {
		case "euribor":
			sendMessage(chat, "1 eoeriebor ies %s procint, wat it dan oiek ies.", euriborRate.get());
			break;
		case "wuv":
			BigDecimal wuv = euriborRate.get().add(properties.getWuvMarkup());
			BigDecimal amount = wuv.multiply(properties.getMortgageSebster()).divide(BigDecimal.valueOf(12 * 100), 2, HALF_UP);
			sendMessage(chat, "1 woev ies %s procint. Voier Sibstir was dat %s eoero pir maand.", wuv, amount);
			break;
		case "wav":
			BigDecimal wav = euriborRate.get().add(properties.getWavMarkup());
			sendMessage(chat, "1 wav ies %s procint.", wav);
			break;
		default:
			throw new IllegalArgumentException("Unknown rate: " + rate);
		}
	}

	private Optional<BigDecimal> lookUpEuriborRate() {
		try {
			Matcher matcher = EURIBOR_PATTERN.matcher(IOUtils.toString(EURIBOR_URI, UTF_8));
			return matcher.find() ? Optional.of(new BigDecimal(matcher.group(1))) : Optional.empty();
		} catch (IOException e) {
			log.warn("Error fetching euribor rate", e);
			return Optional.empty();
		}
	}

}
