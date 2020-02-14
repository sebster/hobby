package com.sebster.weereld.hobbes.plugins.plato;

import static java.util.regex.Pattern.compile;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;

@Component
@EnableConfigurationProperties(PlatoProperties.class)
public class PlatoPlugin extends BasePlugin {

	private static final Pattern PLATO_RANDOM_ENTRY_PATTERN = compile("(?i)^plato$");
	private static final Pattern PLATO_SUBSCRIBE_PATTERN = compile("(?i)^plato aan$");
	private static final Pattern PLATO_UNSUBSCRIBE_PATTERN = compile("(?i)^plato uit$");

	@Autowired
	private PlatoService platoService;

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
		sendMessage(telegramChat, "plato - krijg een willekeurig citaat uit de Stanford Encyclopedia of Philosophy.\n"
				+ "plato aan - abonneer op citaten.\n"
				+ "plato uit - stop je abonnement");
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		String text = message.getText().trim().toLowerCase();
		TelegramChat chat = message.getChat();

		if (PLATO_RANDOM_ENTRY_PATTERN.matcher(text).matches()) {
			sendQuoteFromRandomEntry(chat.getId());
			return;
		}

		if (PLATO_SUBSCRIBE_PATTERN.matcher(text).matches()) {
			platoService.subscribe(chat.getId());
			return;
		}

		if (PLATO_UNSUBSCRIBE_PATTERN.matcher(text).matches()) {
			platoService.unsubscribe(chat.getId());
		}

	}

	void sendQuoteFromRandomEntry(long chatId) {
		sendMessage(chatId, platoService.getQuoteFromRandomEntry());
	}

}
