package com.sebster.weereld.hobbes.plugins.plato;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlatoPlugin extends BasePlugin {

	private final PlatoService platoService;

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
		sendMessage(telegramChat,
				"plato - krijg een citaat uit de Stanford Encyclopedia of Philosophy."
		);
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		String text = message.getText().trim().toLowerCase();

		if (!text.matches("plato")) {
			return;
		}

		TelegramChat chat = message.getChat();

		if (Objects.equals(text, "plato")) {
			sendQuoteFromRandomEntry(chat.getId());
		}
	}

	void sendQuoteFromRandomEntry(long chatId) {
		sendMessage(chatId, platoService.quoteFromRandomEntry());
	}

}
