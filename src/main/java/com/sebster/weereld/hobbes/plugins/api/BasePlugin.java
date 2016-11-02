package com.sebster.weereld.hobbes.plugins.api;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sebster.telegram.api.TelegramEmoji;
import com.sebster.telegram.api.TelegramSendMessageOptions;
import com.sebster.telegram.api.TelegramService;
import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.messages.TelegramMessage;
import com.sebster.telegram.api.data.messages.TelegramMessageVisitorAdapter;
import com.sebster.weereld.hobbes.people.Person;
import com.sebster.weereld.hobbes.people.PersonRepository;

public abstract class BasePlugin extends TelegramMessageVisitorAdapter implements Plugin {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected Clock clock;

	@Autowired
	protected TelegramService telegramService;

	@Autowired
	protected PersonRepository personRepository;

	@Override
	@Transactional
	public void receiveMessage(TelegramMessage telegramMessage) {
		telegramMessage.accept(this);
	}

	@Override
	public void visitMessage(TelegramMessage message) {
		// Ignore message by default.
	}

	protected void sendMessage(TelegramChat chat, String text, Object... args) {
		sendMessage(chat.getId(), text, args);
	}

	protected void sendMessage(long chatId, String text, Object... args) {
		telegramService.sendMessage(chatId, TelegramEmoji.expand(format(text, args)));
	}

	protected void sendMessage(TelegramChat chat, TelegramSendMessageOptions options, String text, Object... args) {
		sendMessage(chat.getId(), options, text, args);
	}

	protected void sendMessage(long chatId, TelegramSendMessageOptions options, String text, Object... args) {
		telegramService.sendMessage(chatId, options, TelegramEmoji.expand(format(text, args)));
	}

	protected Optional<Integer> getFromUserId(TelegramMessage message) {
		return message.getFrom().map(TelegramUser::getId);
	}

	protected Optional<Person> getFrom(TelegramMessage message) {
		return getFromUserId(message).flatMap(personRepository::findByTelegramUserId);
	}

	protected Optional<String> getFirstName(Optional<TelegramUser> user) {
		return user.map(TelegramUser::getFirstName);
	}

	protected boolean isMe(String nick) {
		return equalsIgnoreCase(nick, "mij");
	}

	protected Optional<Person> meOrPersonByNick(TelegramMessage message, String nick) {
		return isMe(nick) ? getFrom(message) : personRepository.findByNickIgnoreCase(nick);
	}

	protected LocalDate date() {
		return LocalDate.now(clock);
	}

	protected LocalDate date(ZoneId zone) {
		return LocalDate.now(clock.withZone(zone));
	}

	protected LocalTime time() {
		return LocalTime.now(clock);
	}

	protected LocalTime time(ZoneId zone) {
		return LocalTime.now(clock.withZone(zone));
	}

	protected LocalDateTime dateTime() {
		return LocalDateTime.now(clock);
	}

	protected LocalDateTime dateTime(ZoneId zone) {
		return LocalDateTime.now(clock.withZone(zone));
	}

}
