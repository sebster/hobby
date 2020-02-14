package com.sebster.weereld.hobbes.plugins.plato;

import static com.sebster.telegram.botapi.TelegramSendMessageOptions.html;
import static java.util.regex.Pattern.compile;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.stereotype.Component;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoMessageService;
import com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoSubscriptionService;

@Component
@EnableConfigurationProperties(PlatoProperties.class)
public class PlatoPlugin extends BasePlugin implements PlatoMessageService {

	private static final Pattern PLATO_RANDOM_ENTRY_PATTERN = compile("(?i)^plato$");
	private static final Pattern PLATO_SUBSCRIBE_PATTERN = compile("(?i)^plato aan *(\\d+[a-zA-Z]{0,2})? *(\\d+[a-zA-Z]{0,2})?");
	private static final Pattern PLATO_UNSUBSCRIBE_PATTERN = compile("(?i)^plato uit$");

	@Autowired
	private PlatoProperties properties;

	@Autowired
	private PlatoSubscriptionService subscriptionService;

	@Override
	public String getName() {
		return "plato";
	}

	@Override
	public String getDescription() {
		return "Citaten uit de Stanford Encyclopedia of Philosophy.";
	}

	@Override
	public void showHelp(TelegramChat telegramChat) {
		sendMessage(telegramChat, html(),
				"<b>plato</b> - krijg een willekeurig citaat uit de Stanford Encyclopedia of Philosophy.\n"
						+ "<b>plato aan</b> - krijg af en toe een citaat van plato \n"
						+ "<b>plato aan &lt;interval&gt;</b> - krijg elke &lt;interval&gt; een citaat (voorbeeld: plato aan 1d) \n"
						+ "<b>plato uit</b> - stop je abonnement");
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		String text = message.getText().trim().toLowerCase();
		long chatId = message.getChat().getId();

		if (PLATO_RANDOM_ENTRY_PATTERN.matcher(text).matches()) {
			sendQuoteFromRandomEntry(chatId);
			return;
		}

		Matcher subscribeMatcher = PLATO_SUBSCRIBE_PATTERN.matcher(text);
		if (subscribeMatcher.matches()) {
			if (subscribeMatcher.group(2) != null) {
				Duration lowerBound = DurationStyle.detectAndParse(subscribeMatcher.group(1));
				Duration upperBound = DurationStyle.detectAndParse(subscribeMatcher.group(2));
				subscriptionService.subscribe(chatId, lowerBound, upperBound);
				sendMessage(chatId, String.format("Je ontvangt vanaf nu elke %s tot %s een citaat.", subscribeMatcher.group(1),
						subscribeMatcher.group(2)));
			} else if (subscribeMatcher.group(1) != null) {
				Duration interval = DurationStyle.detectAndParse(subscribeMatcher.group(1));
				subscriptionService.subscribe(chatId, interval);
				sendMessage(chatId, String.format("Je ontvangt vanaf nu elke %s een citaat.", subscribeMatcher.group(1)));
			} else {
				subscriptionService.subscribe(chatId);
				StringBuilder sb = new StringBuilder("Je ontvangt nu elke ");
				sb.append(DurationStyle.SIMPLE.print(properties.getIntervalLowerBound(), ChronoUnit.HOURS));
				if (properties.getIntervalUpperBound() != Duration.ZERO) {
					sb.append(" tot ").append(DurationStyle.SIMPLE.print(properties.getIntervalUpperBound(), ChronoUnit.HOURS));
				}
				sb.append(" een citaat.");
				sendMessage(chatId, sb.toString());
			}
			return;
		}

		if (PLATO_UNSUBSCRIBE_PATTERN.matcher(text).matches()) {
			subscriptionService.unsubscribe(chatId);
			sendMessage(chatId, "Ok, ik houd er mee op.");
		}

	}

	public void sendQuoteFromRandomEntry(long chatId) {
		sendMessage(chatId, PlatoEntryService.getQuoteFromRandomEntry());
	}

}
