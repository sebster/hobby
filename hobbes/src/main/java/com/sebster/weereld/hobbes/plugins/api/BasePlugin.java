package com.sebster.weereld.hobbes.plugins.api;

import static com.sebster.weereld.hobbes.people.PersonSpecification.withNick;
import static com.sebster.weereld.hobbes.people.PersonSpecification.withTelegramUserId;
import static java.lang.String.format;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.sebster.repository.api.Repository;
import com.sebster.telegram.botapi.TelegramEmoji;
import com.sebster.telegram.botapi.TelegramSendMessageOptions;
import com.sebster.telegram.botapi.TelegramService;
import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramUser;
import com.sebster.telegram.botapi.messages.TelegramMessage;
import com.sebster.telegram.botapi.messages.TelegramMessageVisitorAdapter;
import com.sebster.weereld.hobbes.people.Person;
import lombok.NonNull;

public abstract class BasePlugin extends TelegramMessageVisitorAdapter implements Plugin {

	protected TelegramService telegramService;
	protected Repository<Person> personRepository;

	private final AtomicBoolean enabled = new AtomicBoolean(false);

	@Override
	@Transactional
	public void receiveMessage(TelegramMessage message) {
		if (isEnabled()) {
			message.accept(this);
		}
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
		return getFromUserId(message).flatMap(userId -> personRepository.findOne(withTelegramUserId(userId)));
	}

	protected boolean isMe(@NonNull String nick) {
		return nick.equalsIgnoreCase("mij");
	}

	protected Optional<Person> meOrPersonByNick(@NonNull TelegramMessage message, @NonNull String nick) {
		return isMe(nick) ? getFrom(message) : personRepository.findOne(withNick(nick));
	}

	@Override
	public void enable() {
		enabled.set(true);
		onEnable();
	}

	protected void onEnable() {
	}

	@Override
	public void disable() {
		enabled.set(false);
		onDisable();
	}

	protected void onDisable() {
	}

	@Override
	public boolean isEnabled() {
		return enabled.get();
	}

	@Autowired
	public void setTelegramService(TelegramService telegramService) {
		this.telegramService = telegramService;
	}

	@Autowired
	public void setPersonRepository(Repository<Person> personRepository) {
		this.personRepository = personRepository;
	}

}
