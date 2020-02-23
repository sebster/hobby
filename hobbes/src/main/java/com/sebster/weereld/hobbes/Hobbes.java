package com.sebster.weereld.hobbes;

import static com.sebster.weereld.hobbes.utils.TimeUtils.sleep;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.max;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sebster.telegram.botapi.TelegramService;
import com.sebster.telegram.botapi.TelegramUpdate;
import com.sebster.telegram.botapi.data.TelegramUser;
import com.sebster.telegram.botapi.messages.TelegramMessage;
import com.sebster.weereld.hobbes.plugins.api.Plugin;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(HobbesProperties.class)
@AllArgsConstructor
@Slf4j
public class Hobbes implements CommandLineRunner {

	private static final Duration POLL_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration INITIAL_ERROR_DELAY = Duration.ofSeconds(1);
	private static final Duration MAX_ERROR_DELAY = Duration.ofSeconds(30);

	private final @NonNull HobbesProperties hobbesProperties;
	private final @NonNull TelegramService telegramService;
	private final @NonNull List<Plugin> plugins;

	@Override
	public void run(String... args) {
		logEnabledPlugins();
		logWhiteLists();

		int lastUpdateId = -1;
		Duration duration = INITIAL_ERROR_DELAY;
		while (true) {
			try {
				List<TelegramUpdate> updates = telegramService.getUpdates(lastUpdateId + 1, POLL_TIMEOUT);
				for (TelegramUpdate update : updates) {
					log.debug("Received update: " + update);
					processUpdate(update);
					lastUpdateId = update.getUpdateId();
				}
				duration = INITIAL_ERROR_DELAY;
			} catch (RuntimeException e) {
				log.error("Error reading updates from telegram; sleeping " + duration + "...", e);
				sleep(duration);
				duration = max(duration.multipliedBy(2), MAX_ERROR_DELAY);
			}
		}
	}

	private void processUpdate(TelegramUpdate update) {
		if (update.getMessage().isEmpty()) {
			log.warn("Update without message received: " + update);
			return;
		}
		TelegramMessage message = update.getMessage().get();
		if (!isWhiteListed(message)) {
			log.warn("Non-whitelisted update received: " + update);
			return;
		}
		for (Plugin plugin : plugins) {
			try {
				plugin.receiveMessage(message);
			} catch (RuntimeException e) {
				log.error("Plugin failed: plugin=" + plugin.getClass() + ", update=" + update, e);
			}
		}
	}

	private boolean isWhiteListed(TelegramMessage telegramMessage) {
		return isWhiteListedChat(telegramMessage) || isWhiteListedFrom(telegramMessage);
	}

	private boolean isWhiteListedFrom(TelegramMessage telegramMessage) {
		int from = telegramMessage.getFrom().map(TelegramUser::getId).orElse(-1);
		return hobbesProperties.getTelegramFromWhiteList().stream().anyMatch(allowedFrom -> allowedFrom == from);
	}

	private boolean isWhiteListedChat(TelegramMessage telegramMessage) {
		long chat = telegramMessage.getChat().getId();
		return hobbesProperties.getTelegramChatWhiteList().stream().anyMatch(allowedChat -> allowedChat == chat);
	}

	private void logEnabledPlugins() {
		List<String> pluginNames = plugins.stream().map(Plugin::getName).collect(toList());
		log.info("Running with the following plugins enabled: {}", pluginNames);
	}

	private void logWhiteLists() {
		log.info("From white list: {}", hobbesProperties.getTelegramFromWhiteList());
		log.info("Chat white list: {}", hobbesProperties.getTelegramChatWhiteList());
	}

	public static void main(String[] args) {
		SpringApplication.run(Hobbes.class, args);
	}

}
