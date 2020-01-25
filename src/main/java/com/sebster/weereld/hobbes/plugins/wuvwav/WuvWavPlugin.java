package com.sebster.weereld.hobbes.plugins.wuvwav;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;

@Component
public class WuvWavPlugin extends BasePlugin {

	private static final URI EURIBOR_URI = URI.create("http://euribor-rates.eu");
	private static final Pattern EURIBOR_PATTERN = compile(
			"(?im)<td nowrap>\\s*1 month\\s*</td>\\s*<td align=\"center\">\\s*(-?\\d+\\.\\d+)%\\s*</td>");

	@Value("${wuvwav.wuv.markup:2.0}")
	private BigDecimal wuvMarkup;

	@Value("${wuvwav.wav.markup:3.0}")
	private BigDecimal wavMarkup;

	@Value("${wuvwav.mortgage-sebster:100000}")
	private BigDecimal mortgageSebster;

	@Override
	public void visitTextMessage(TelegramTextMessage textMessage) {
		String text = textMessage.getText().trim();

		if (!text.matches("1 euribor|1 wuv|1 wav")) {
			return;
		}

		TelegramChat chat = textMessage.getChat();

		Optional<BigDecimal> euriborRate = lookUpEuriborRate();
		if (!euriborRate.isPresent()) {
			sendMessage(chat, "Ik weit ivin niet oiveil %s ies.", text.replaceAll("u", "oe").replaceAll("i", "ie"));
			return;
		}

		switch (text) {
		case "1 euribor":
			sendMessage(chat, "1 eoeriebor ies %s procint, wat it dan oiek ies.", euriborRate.get());
			break;
		case "1 wuv":
			BigDecimal wuv = euriborRate.get().add(wuvMarkup);
			BigDecimal amount = wuv.multiply(mortgageSebster).divide(BigDecimal.valueOf(12 * 100), 2, HALF_UP);
			sendMessage(chat, "1 woev ies %s procint. Voier Sibstir was dat %s eoero pir maand.", wuv, amount);
			break;
		case "1 wav":
			BigDecimal wav = euriborRate.get().add(wavMarkup);
			sendMessage(chat, "1 wav ies %s procint.", wav);
			break;
		default:
			throw new IllegalStateException("Unmatched text: " + text);
		}
	}

	private Optional<BigDecimal> lookUpEuriborRate() {
		try {
			Matcher matcher = EURIBOR_PATTERN.matcher(IOUtils.toString(EURIBOR_URI, UTF_8));
			return matcher.find() ? Optional.of(new BigDecimal(matcher.group(1))) : Optional.empty();
		} catch (IOException e) {
			logger.warn("Error fetching euribor rate", e);
			return Optional.empty();
		}
	}

}
