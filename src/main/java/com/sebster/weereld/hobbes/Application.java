package com.sebster.weereld.hobbes;

import static com.sebster.weereld.hobbes.utils.TimeUtils.sleep;
import static org.apache.commons.lang3.ObjectUtils.max;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sebster.telegram.api.TelegramService;
import com.sebster.telegram.api.data.TelegramUpdate;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.messages.TelegramMessage;
import com.sebster.weereld.hobbes.plugins.api.Plugin;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

	private static final Duration POLL_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration INITIAL_ERROR_DELAY = Duration.ofSeconds(1);
	private static final Duration MAX_ERROR_DELAY = Duration.ofSeconds(30);

	private final Logger logger = LoggerFactory.getLogger(Application.class);

	@Value("${telegram.from.white-list}")
	private int[] fromWhiteList;

	@Value("${telegram.chat.white-list}")
	private int[] chatWhiteList;

	@Autowired
	private TelegramService telegramService;

	@Autowired
	private List<Plugin> plugins;

	@Override
	public void run(String... args) throws Exception {
		int lastUpdateId = -1;
		Duration duration = INITIAL_ERROR_DELAY;
		while (true) {
			try {
				List<TelegramUpdate> updates = telegramService.getUpdates(lastUpdateId + 1, POLL_TIMEOUT);
				for (TelegramUpdate update : updates) {
					logger.debug("Received update: " + update);
					processUpdate(update);
					lastUpdateId = update.getUpdateId();
				}
				duration = INITIAL_ERROR_DELAY;
			} catch (RuntimeException e) {
				logger.error("Error reading updates from telegram; sleeping " + duration + "...", e);
				sleep(duration);
				duration = max(duration.multipliedBy(2), MAX_ERROR_DELAY);
			}
		}
	}

	private void processUpdate(TelegramUpdate update) {
		if (!update.getMessage().isPresent()) {
			logger.warn("Update without message received: " + update);
			return;
		}
		TelegramMessage message = update.getMessage().get();
		if (!isWhiteListed(message)) {
			logger.warn("Non-whitelisted update received: " + update);
			return;
		}
		for (Plugin plugin : plugins) {
			try {
				plugin.receiveMessage(message);
			} catch (RuntimeException e) {
				logger.error("Plugin failed: plugin=" + plugin.getClass() + ", update=" + update, e);
			}
		}
	}

	private boolean isWhiteListed(TelegramMessage telegramMessage) {
		long chat = telegramMessage.getChat().getId();
		if (Arrays.stream(chatWhiteList).anyMatch(allowedChat -> allowedChat == chat)) {
			return true;
		}
		int from = telegramMessage.getFrom().map(TelegramUser::getId).orElse(-1);
		if (Arrays.stream(fromWhiteList).anyMatch(allowedFrom -> allowedFrom == from)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
