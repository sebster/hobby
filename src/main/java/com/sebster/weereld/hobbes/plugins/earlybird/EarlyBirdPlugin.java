package com.sebster.weereld.hobbes.plugins.earlybird;

import static com.sebster.telegram.api.TelegramSendMessageOptions.html;
import static com.sebster.weereld.hobbes.utils.StringUtils.formatIfPresent;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.messages.TelegramMessage;
import com.sebster.telegram.api.data.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.people.Person;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;

@Component
public class EarlyBirdPlugin extends BasePlugin {

	private static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd");
	private static final DateTimeFormatter SHORT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

	private static final Pattern LOCAL_TIME_PATTERN = compile("(?i)Hoe laat is het bij (\\w+)\\??");
	private static final Pattern CURRENT_EARLY_BIRD_PATTERN = compile("(?i)voorlopig");
	private static final Pattern WEEK_LIST_PATTERN = compile("(?i)vv|vroegevogels");

	private static final LocalTime VROEGE_VOGEL_CUTOFF_TIME = LocalTime.parse("03:30:00");

	@Autowired
	private EarlyBirdRepository earlyBirdRepository;

	@Override
	public void visitTextMessage(TelegramTextMessage textMessage) {

		visitMessage(textMessage);

		String text = textMessage.getText().trim();
		Matcher matcher;

		matcher = LOCAL_TIME_PATTERN.matcher(text);
		if (matcher.matches()) {
			String nick = matcher.group(1);
			showLocalTime(textMessage, nick);
			return;
		}

		matcher = CURRENT_EARLY_BIRD_PATTERN.matcher(text);
		if (matcher.matches()) {
			showCurrentEarlyBird(textMessage);
			return;
		}

		matcher = WEEK_LIST_PATTERN.matcher(text);
		if (matcher.matches()) {
			showEarlyBirdWeekList(textMessage.getChat());
			return;
		}
	}

	@Override
	public void visitMessage(TelegramMessage message) {
		updateEarlyBirdForMessage(message);
		checkForWinner(message.getChat());
	}

	private void showLocalTime(TelegramMessage message, String nick) {
		Optional<Person> person = meOrPersonByNick(message, nick);
		TelegramChat chat = message.getChat();
		if (person.isPresent() && person.get().getZone().isPresent()) {
			String time = SHORT_TIME_FORMAT.format(time(person.get().getZone().get()));
			sendMessage(chat, "Bai %s ies it %s.", person.get().getNick(), time);
		} else {
			String name = formatIfPresent(message.getFrom().map(TelegramUser::getFirstName), ", %s");
			sendMessage(chat, "Iek ib chein iedoi" + name + ".");
		}
	}

	private void showCurrentEarlyBird(TelegramTextMessage message) {
		Optional<EarlyBird> ebOpt = earlyBirdRepository.findFirstByDateOrderByWakeUpTime(earlyBirdDate());
		if (ebOpt.isPresent()) {
			EarlyBird eb = ebOpt.get();
			sendMessage(message.getChat(), "De %svroige voegil van vandaag ies %s oem %s.",
					eb.isWinner() ? "" : "voierloepiege ", eb.nick(), SHORT_TIME_FORMAT.format(eb.wakeUpTime()));
		}
	}

	private void showEarlyBirdWeekList(TelegramChat chat) {
		StringBuilder message = new StringBuilder();
		message.append("In dan noe di vroige voegils van di afgiloepin weik:");
		message.append("\n<pre>");
		List<EarlyBird> ebs = earlyBirdRepository.findFirst7ByWinnerTrueOrderByDateDesc();
		Collections.reverse(ebs);
		for (EarlyBird eb : ebs) {
			message.append(format("%-10s %s %s %s\n", dayName(eb.date()), SHORT_DATE_FORMAT.format(eb.date()),
					SHORT_TIME_FORMAT.format(eb.wakeUpTime()), eb.nick()));
		}
		message.append("</pre>");
		sendMessage(chat, html(), message.toString());
	}

	private void updateEarlyBirdForMessage(TelegramMessage message) {
		Optional<Integer> fromIdOpt = getFromUserId(message);
		if (!fromIdOpt.isPresent()) {
			// This message is not from a user.
			return;
		}
		int fromId = fromIdOpt.get();

		Optional<Person> personOpt = personRepository.findByTelegramUserId(fromId);
		if (!personOpt.isPresent()) {
			// This message is from a telegram user we don't know about.
			return;
		}
		Person person = personOpt.get();

		Optional<ZoneId> zoneOpt = person.getZone();
		if (!zoneOpt.isPresent()) {
			// The time zone of the telegram user is unknown.
			return;
		}
		ZoneId zone = zoneOpt.get();

		LocalDateTime ebDateTime = LocalDateTime.ofInstant(message.getDate().toInstant(), zone);
		LocalDate ebDate = earlyBirdDate(ebDateTime);
		Optional<EarlyBird> ebOpt = earlyBirdRepository.findByUserIdAndDate(fromId, ebDate);
		if (ebOpt.isPresent()) {
			// This person's wake-up time is already registered for today.
			return;
		}

		// Get the current early bird before we save the new one.
		Optional<EarlyBird> oldEb = earlyBirdRepository.findFirstByDateOrderByWakeUpTime(ebDate);

		EarlyBird eb = new EarlyBird(person.getNick(), ebDate, ebDateTime.toLocalTime());
		if (isWinner(eb)) {
			eb.markWinner();
		}
		eb = earlyBirdRepository.save(eb);

		TelegramChat chat = message.getChat();
		if (!oldEb.isPresent() && eb.isWinner()) {
			sendMessage(chat, "De vroige voegil van %s ies chiwoerdin: %s! Gifiliecieteird!", eb.date(), eb.nick());
		} else if (!oldEb.isPresent()) {
			sendMessage(chat, "Jai maakt noeg kans oep de vroige voegil vandaag, %s!", eb.nick());
		} else if (eb.wakeUpTime().isBefore(oldEb.get().wakeUpTime())) {
			sendMessage(chat, "Jai ibt de vroigivoegil van %s afgipiekt, %s!", oldEb.get().nick(), eb.nick());
		}
	}

	private void checkForWinner(TelegramChat chat) {
		Optional<EarlyBird> ebOpt = earlyBirdRepository.findFirstByDateOrderByWakeUpTime(earlyBirdDate());
		if (!ebOpt.isPresent() || ebOpt.get().isWinner()) {
			// We already have a winner.
			return;
		}
		EarlyBird eb = ebOpt.get();
		if (isWinner(eb)) {
			eb.markWinner();
			sendMessage(chat, "De vroige voegil van %s ies chiwoerdin: %s! Gifiliecieteird!", eb.date(), eb.nick());
		}
	}

	private boolean isWinner(EarlyBird eb) {
		List<Person> telegramUsers = personRepository.findByTelegramUserIdIsNotNull();
		return telegramUsers.stream().noneMatch(person -> canStillBecomeEarlyBird(person, eb));
	}

	private boolean canStillBecomeEarlyBird(Person person, EarlyBird eb) {
		if (!person.getZone().isPresent()) {
			return false;
		}
		LocalDateTime personDateTime = dateTime(person.getZone().get());
		if (earlyBirdDate(personDateTime).isAfter(eb.date())) {
			return false;
		}
		return personDateTime.toLocalTime().isBefore(eb.wakeUpTime());
	}

	private LocalDate earlyBirdDate() {
		return earlyBirdDate(dateTime());
	}

	private LocalDate earlyBirdDate(LocalDateTime dateTime) {
		LocalDate date = dateTime.toLocalDate();
		if (dateTime.toLocalTime().isBefore(VROEGE_VOGEL_CUTOFF_TIME)) {
			date = date.minusDays(1);
		}
		return date;
	}

	private String dayName(LocalDate date) {
		switch (date.getDayOfWeek()) {
		case MONDAY:
			return "Maandag";
		case TUESDAY:
			return "Diensdag";
		case WEDNESDAY:
			return "Woinsdag";
		case THURSDAY:
			return "Doendirdag";
		case FRIDAY:
			return "Vraidag";
		case SATURDAY:
			return "Zatirdag";
		case SUNDAY:
			return "Zoendag";
		default:
			throw new IllegalArgumentException("Invalid day of week: " + date.getDayOfWeek());
		}
	}

}
