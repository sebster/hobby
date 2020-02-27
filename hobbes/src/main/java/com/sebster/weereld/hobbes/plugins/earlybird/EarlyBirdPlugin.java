package com.sebster.weereld.hobbes.plugins.earlybird;

import static com.sebster.repository.api.PageRequest.page;
import static com.sebster.repository.api.properties.orders.PropertyOrder.orderBy;
import static com.sebster.telegram.botapi.TelegramSendMessageOptions.html;
import static com.sebster.weereld.hobbes.people.PersonSpecification.hasTelegramUserId;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.DATE;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.WAKE_UP_TIME;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBirdSpecification.forNickOnDate;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBirdSpecification.isWinner;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBirdSpecification.onDate;
import static com.sebster.weereld.hobbes.utils.StringUtils.formatIfPresent;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.sebster.repository.api.Repository;
import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramUser;
import com.sebster.telegram.botapi.messages.TelegramMessage;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.people.Person;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
@EnableConfigurationProperties(EarlyBirdProperties.class)
public class EarlyBirdPlugin extends BasePlugin {

	private static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd");
	private static final DateTimeFormatter SHORT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

	private static final Pattern LOCAL_TIME_PATTERN = compile("(?i)Hoe laat is het bij (\\w+)\\??");
	private static final Pattern CURRENT_EARLY_BIRD_PATTERN = compile("(?i)voorlopig");
	private static final Pattern WEEK_LIST_PATTERN = compile("(?i)vv|vroegevogels");

	private static final LocalTime VROEGE_VOGEL_CUTOFF_TIME = LocalTime.parse("03:30:00");

	private final @NonNull Repository<EarlyBird> earlyBirdRepository;
	private final @NonNull EarlyBirdProperties properties;

	@Override
	public String getName() {
		return "vroegevogel";
	}

	@Override
	public String getDescription() {
		return "Wie is er het eerste op en maakt het meeste van zijn dag?";
	}

	@Override
	public void showHelp(TelegramChat chat) {
		sendMessage(chat,
				"Hoe laat is het bij <naam>?\n" +
						"voorlopig - toon de voorlopige vroege vogels\n" +
						"vv|vroegevogels - toon de vroege vogels van de afgelopen week\n"
		);
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {

		visitMessage(message);

		String text = message.getText().trim();
		Matcher matcher;

		matcher = LOCAL_TIME_PATTERN.matcher(text);
		if (matcher.matches()) {
			String nick = matcher.group(1);
			showLocalTime(message, nick);
			return;
		}

		matcher = CURRENT_EARLY_BIRD_PATTERN.matcher(text);
		if (matcher.matches()) {
			showCurrentEarlyBird(message);
			return;
		}

		matcher = WEEK_LIST_PATTERN.matcher(text);
		if (matcher.matches()) {
			showEarlyBirdWeekList(message.getChat());
			return;
		}
	}

	@Override
	public void visitMessage(TelegramMessage message) {
		if (!isEarlyBirdChat(message)) {
			return;
		}
		updateEarlyBirdForMessage(message);
		checkForWinner(message.getChat());
	}

	private boolean isEarlyBirdChat(TelegramMessage message) {
		return properties.getChatIds().stream().anyMatch(chatId -> chatId == message.getChat().getId());
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
		TelegramChat chat = message.getChat();
		Optional<EarlyBird> ebOpt = earlyBirdRepository.findFirst(onDate(earlyBirdDate(), chat.getId()), orderBy(WAKE_UP_TIME));
		if (ebOpt.isPresent()) {
			EarlyBird eb = ebOpt.get();
			sendMessage(chat, "De %svroige voegil van vandaag ies %s oem %s.",
					eb.isWinner() ? "" : "voierloepiege ", eb.getNick(), SHORT_TIME_FORMAT.format(eb.getWakeUpTime()));
		}
	}

	private void showEarlyBirdWeekList(TelegramChat chat) {
		StringBuilder message = new StringBuilder();
		message.append("In dan noe di vroige voegils van di afgiloepin weik:");
		message.append("\n<pre>");
		List<EarlyBird> ebs = new ArrayList<>(
				earlyBirdRepository
						.findAll(isWinner(chat.getId()), orderBy(DATE).reversed(), page(0).pageSize(7))
						.getItems()
		);
		Collections.reverse(ebs);
		for (EarlyBird eb : ebs) {
			message.append(format("%-10s %s %s %s\n", dayName(eb.getDate()), SHORT_DATE_FORMAT.format(eb.getDate()),
					SHORT_TIME_FORMAT.format(eb.getWakeUpTime()), eb.getNick()));
		}
		message.append("</pre>");
		sendMessage(chat, html(), message.toString());
	}

	private void updateEarlyBirdForMessage(TelegramMessage message) {
		Optional<Person> personOpt = getFrom(message);
		if (personOpt.isEmpty()) {
			// This message is not from a known telegram user.
			return;
		}
		Person person = personOpt.get();

		Optional<ZoneId> zoneOpt = person.getZone();
		if (zoneOpt.isEmpty()) {
			// The time zone of the telegram user is unknown.
			return;
		}
		ZoneId zone = zoneOpt.get();

		TelegramChat chat = message.getChat();
		LocalDateTime ebDateTime = LocalDateTime.ofInstant(message.getDate().toInstant(), zone);
		LocalDate ebDate = earlyBirdDate(ebDateTime);
		Optional<EarlyBird> ebOpt = earlyBirdRepository.findOne(forNickOnDate(ebDate, person.getNick(), chat.getId()));
		if (ebOpt.isPresent()) {
			// This person's wake-up time is already registered for today.
			return;
		}

		// Get the current early bird before we save the new one.
		Optional<EarlyBird> oldEb = earlyBirdRepository.findFirst(onDate(ebDate, chat.getId()), orderBy(WAKE_UP_TIME));

		EarlyBird eb = new EarlyBird(chat.getId(), person.getNick(), ebDate, ebDateTime.toLocalTime());
		if (hasWon(eb)) {
			eb.markWinner();
		}
		earlyBirdRepository.add(eb);

		if (oldEb.isEmpty() && eb.isWinner()) {
			sendMessage(chat, "De vroige voegil van %s ies chiwoerdin: %s! Gifiliecieteird!", eb.getDate(), eb.getNick());
		} else if (oldEb.isEmpty()) {
			sendMessage(chat, "Jai maakt noeg kans oep de vroige voegil vandaag, %s!", eb.getNick());
		} else if (eb.getWakeUpTime().isBefore(oldEb.get().getWakeUpTime())) {
			sendMessage(chat, "Jai ibt de vroigivoegil van %s afgipiekt, %s!", oldEb.get().getNick(), eb.getNick());
		}
	}

	private void checkForWinner(TelegramChat chat) {
		Optional<EarlyBird> ebOpt = earlyBirdRepository.findFirst(onDate(earlyBirdDate(), chat.getId()), orderBy(WAKE_UP_TIME));
		if (ebOpt.isEmpty() || ebOpt.get().isWinner()) {
			// We already have a winner.
			return;
		}
		EarlyBird eb = ebOpt.get();
		if (hasWon(eb)) {
			eb.markWinner();
			sendMessage(chat, "De vroige voegil van %s ies chiwoerdin: %s! Gifiliecieteird!", eb.getDate(), eb.getNick());
		}
	}

	private boolean hasWon(EarlyBird eb) {
		Stream<Person> telegramUsers = personRepository.findAll(hasTelegramUserId());
		return telegramUsers.noneMatch(person -> canStillBecomeEarlyBird(person, eb));
	}

	private boolean canStillBecomeEarlyBird(Person person, EarlyBird eb) {
		if (person.getZone().isEmpty()) {
			return false;
		}
		LocalDateTime personDateTime = dateTime(person.getZone().get());
		if (earlyBirdDate(personDateTime).isAfter(eb.getDate())) {
			return false;
		}
		return personDateTime.toLocalTime().isBefore(eb.getWakeUpTime());
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
