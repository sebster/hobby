package com.sebster.weereld.hobbes.plugins.plugininfo;

import static com.sebster.telegram.botapi.TelegramSendMessageOptions.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import com.sebster.weereld.hobbes.plugins.api.Plugin;
import lombok.NonNull;

@Component
public class PluginInfoPlugin extends BasePlugin {

	private final @NonNull List<Plugin> plugins;

	public PluginInfoPlugin(@NonNull List<Plugin> plugins) {
		this.plugins = new ArrayList<>(plugins);
		this.plugins.add(this);
	}

	@Override
	public String getName() {
		return "plugins";
	}

	@Override
	public String getDescription() {
		return "Welke plugins zijn er allemaal?";
	}

	@Override
	public void showHelp(TelegramChat chat) {
		sendMessage(chat,
				"plugins - toon de lijst van beschikbare plugins\n" +
						"help <plugin> - toon de help voor de opgegeven plugin"
		);
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		String text = message.getText().toLowerCase();

		if (text.equals("plugins") || text.equals("help")) {
			listPlugins(message.getChat());
			return;
		}

		if (text.startsWith("help ")) {
			String pluginName = text.substring("help ".length());
			showHelp(message.getChat(), pluginName);
			return;
		}
	}

	private void listPlugins(TelegramChat chat) {
		sendMessage(chat, "De volgende plugins zijn beschikbaar:");
		plugins.stream()
				.filter(Plugin::isEnabled)
				.forEach(plugin -> sendMessage(chat, html(), "<b>" + plugin.getName() + "</b>: " + plugin.getDescription()));
		sendMessage(chat, html(), "Gebruik <code>help &lt;plugin&gt;</code> voor help voor een plugin.");
	}

	private void showHelp(TelegramChat chat, String pluginName) {
		findPlugin(pluginName).ifPresentOrElse(
				plugin -> plugin.showHelp(chat),
				() -> sendMessage(chat, "Er bestaat helemaal geen plugin '" + pluginName + "'...")
		);
	}

	private Optional<Plugin> findPlugin(String pluginName) {
		return plugins.stream()
				.filter(plugin -> plugin.getName().equalsIgnoreCase(pluginName))
				.findFirst();
	}

}
