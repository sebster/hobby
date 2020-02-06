package com.sebster.weereld.hobbes.plugins.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import com.sebster.weereld.hobbes.plugins.api.Plugin;

@Component
public class PluginsPlugin extends BasePlugin {

	private final List<Plugin> plugins;

	public PluginsPlugin(List<Plugin> plugins) {
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
		plugins.forEach(plugin -> sendMessage(chat, plugin.getName() + ": " + plugin.getDescription()));
		sendMessage(chat, "Gebruik 'help <plugin>' voor help voor een plugin.");
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
