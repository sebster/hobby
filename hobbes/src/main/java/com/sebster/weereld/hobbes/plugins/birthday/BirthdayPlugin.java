package com.sebster.weereld.hobbes.plugins.birthday;

import static com.sebster.weereld.hobbes.plugins.birthday.Birthday.withBirthdayOn;
import static com.sebster.weereld.hobbes.plugins.birthday.Birthday.withDay;
import static com.sebster.weereld.hobbes.plugins.birthday.Birthday.withMonth;
import static com.sebster.weereld.hobbes.plugins.birthday.Birthday.withName;
import static com.sebster.weereld.hobbes.plugins.birthday.Birthday.withNames;
import static com.sebster.weereld.hobbes.plugins.birthday.Birthday.withYear;
import static com.sebster.weereld.hobbes.utils.StringUtils.formatIfNotNull;
import static com.sebster.weereld.hobbes.utils.TimeUtils.sleep;
import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toCollection;

import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sebster.telegram.botapi.data.TelegramChat;
import com.sebster.telegram.botapi.data.TelegramUser;
import com.sebster.telegram.botapi.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;

@Component
public class BirthdayPlugin extends BasePlugin {

	private static final Pattern BDAY_TODAY_PATTERN = compile("(?i)^bday$");
	private static final Pattern BDAY_FOR_NAME_PATTERN = compile("(?i)^(?:bday|age) ([a-z]+)(?:\\s*&\\s*([a-z]+))?$");
	private static final Pattern BDAY_FOR_DATE_PATTERN = compile("(?i)^bday (\\d{4}-\\d{2}-\\d{2})$");
	private static final Pattern BDAY_FOR_MONTH_DAY_PATTERN = compile("(?i)^bday (\\d{2})-(\\d{2})$");
	private static final Pattern BDAY_MONTH_PATTERN = compile("(?i)^bday (\\d{1,2})$");
	private static final Pattern BDAY_YEAR_PATTERN = compile("(?i)^bday (\\d{4})$");

	@Value("${birthday.sing.chat-id}")
	private long singChatId;

	@Autowired
	private BirthdayService birthdayService;

	@Override
	public String getName() {
		return "bday";
	}

	@Override
	public String getDescription() {
		return "Vraag naar de verjaardagen en leeftijden van #weereld-gangers.";
	}

	@Override
	public void showHelp(TelegramChat chat) {
		sendMessage(chat,
				"bday - wie is er vandaag jarig?\n" +
						"bday <naam> - wanneer is <naam> geboren?\n" +
						"age <naam> - hoe oud is <naam>?\n" +
						"bday <jjjj-mm-dd> - wie is er op <jjjj-mm-dd> jarig?\n" +
						"bday <mm-dd> - wie is er op <mm-dd> jarig?\n" +
						"bday <maand> - wie is er in <maand> jarig?\n" +
						"bday <jaar> - wie is in <jaar> geboren?\n"
		);
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void sing() {
		LocalDate today = date();
		Set<Birthday> bdays = birthdayService.birthdays(withBirthdayOn(today));
		if (!bdays.isEmpty()) {
			sendMessage(singChatId, "ER IS ER EEN JARIG!");
			sleep(Duration.ofSeconds(1));
			sendMessage(singChatId, "HOERA HOERA!");
			sleep(Duration.ofSeconds(1));
			sendMessage(singChatId, "DAT KUN JE WEL ZIEN DAT %s: ", bdays.size() == 1 ? "IS" : "ZIJN");
			sleep(Duration.ofSeconds(1));
			StringBuilder message = new StringBuilder();
			for (Birthday bday : bdays) {
				message.append(format("%s, die is vandaag %d jaar geworden!\n", bday.getName(), bday.getAge(today)));
			}
			sendMessage(singChatId, message.toString());
		}
	}

	@Override
	public void visitTextMessage(TelegramTextMessage message) {
		TelegramChat chat = message.getChat();
		String from = message.getFrom().map(TelegramUser::getFirstName).orElse(null);
		String text = message.getText().trim();

		Matcher matcher;

		matcher = BDAY_TODAY_PATTERN.matcher(text);
		if (matcher.matches()) {
			showBirthdaysToday(chat);
			return;
		}

		matcher = BDAY_FOR_NAME_PATTERN.matcher(text);
		if (matcher.matches()) {
			String nick1 = matcher.group(1);
			String nick2 = matcher.group(2);
			if (nick2 == null) {
				showBirthdayForName(from, chat, nick1);
			} else {
				showBirthdayForNames(from, chat, nick1, nick2);
			}
			return;
		}

		matcher = BDAY_FOR_DATE_PATTERN.matcher(text);
		if (matcher.matches()) {
			LocalDate date = LocalDate.parse(matcher.group(1));
			showBirthdaysForDate(chat, date);
			return;
		}

		matcher = BDAY_FOR_MONTH_DAY_PATTERN.matcher(text);
		if (matcher.matches()) {
			int month = Integer.parseInt(matcher.group(1));
			int day = Integer.parseInt(matcher.group(2));
			showBirthdaysForMonthDay(chat, month, day);
			return;
		}

		matcher = BDAY_MONTH_PATTERN.matcher(text);
		if (matcher.matches()) {
			int month = Integer.parseInt(matcher.group(1));
			showBirthdaysForMonth(from, chat, month);
			return;
		}

		matcher = BDAY_YEAR_PATTERN.matcher(text);
		if (matcher.matches()) {
			int year = Integer.parseInt(matcher.group(1));
			showBirthdaysForYear(chat, year);
			return;
		}
	}

	private void showBirthdaysToday(TelegramChat chat) {
		LocalDate today = date();
		Set<Birthday> birthdays = birthdayService.birthdays(withMonth(today.getMonthValue()).and(withDay(today.getDayOfMonth())));
		if (birthdays.isEmpty()) {
			sendMessage(chat, "Ik ken niemand die vandaag jarig is!");
		} else {
			StringBuilder message = new StringBuilder();
			for (Birthday bday : birthdays) {
				message.append(format("%s is vandaag jarig en %d jaar geworden! :cake:", bday.getName(), bday.getAge(today)));
			}
			sendMessage(chat, message.toString());
		}
	}

	private void showBirthdayForName(String from, TelegramChat chat, String name) {
		showBirthdayForName(from, chat, name, birthdayService.birthday(withName(name)).orElse(null));
	}

	private void showBirthdayForNames(String from, TelegramChat chat, String name1, String name2) {
		showBirthdayForName(from, chat, name1 + " & " + name2, birthdayService.birthday(withNames(name1, name2)).orElse(null));
	}

	private void showBirthdayForName(String from, TelegramChat chat, String name, Birthday bday) {
		if (bday == null) {
			sendMessage(chat, "Ik ken helemaal geen %s" + formatIfNotNull(from, ", %s") + "!", name);
		} else {
			sendMessage(chat, "%s is geboren op %s en is %d jaar oud.", bday.getName(), bday.getDate(), bday.getAge(date()));
		}
	}

	private void showBirthdaysForDate(TelegramChat chat, LocalDate date) {
		Set<Birthday> bdays = birthdayService.birthdays(withBirthdayOn(date));
		if (bdays.isEmpty()) {
			sendMessage(chat, "Ik ken niemand die op %s jarig %s!", date, date.isBefore(date()) ? "was" : "is");
		} else {
			StringBuilder message = new StringBuilder();
			for (Birthday bday : bdays) {
				int age = bday.getAge(date);
				if (age == 0) {
					message.append(format("%s is op %s geboren!\n", bday.getName(), date));
				} else {
					String verb = date.isBefore(date()) ? "werdt" : "wordt";
					message.append(format("%s %s op %s maar liefst %d jaar oud!\n", bday.getName(), verb, date, age));
				}
			}
			sendMessage(chat, message.toString());
		}
	}

	private void showBirthdaysForMonthDay(TelegramChat chat, int month, int day) {
		showBirthdaysForDate(chat, getNextOccurrence(month, day));
	}

	private void showBirthdaysForMonth(String from, TelegramChat chat, int month) {
		Validate.inclusiveBetween(1, 12, month);
		Map<Integer, Set<Birthday>> bdays = groupBy(birthdayService.birthdays(withMonth(month)), Birthday::getDay);
		if (bdays.isEmpty()) {
			sendMessage(chat, "Ik ken niemand die in %s jarig is" + formatIfNotNull(from, ", %s") + "!", monthName(month));
		} else {
			StringBuilder message = new StringBuilder();
			bdays.forEach((day, dayBdays) -> {
				message.append(format("%02d-%02d: ", month, day));
				message.append(
						dayBdays.stream()
								.map(bday -> format("%s (%d)", bday.getName(), bday.getYear()))
								.collect(joining(", "))
				);
				message.append("\n");
			});
			sendMessage(chat, message.toString());
		}
	}

	private void showBirthdaysForYear(TelegramChat chat, int year) {
		Map<LocalDate, Set<Birthday>> bdays = groupBy(birthdayService.birthdays(withYear(year)), Birthday::getDate);
		if (bdays.isEmpty()) {
			sendMessage(chat, "Ik ken niemand die in %d geboren is!", year);
		} else {
			StringBuilder message = new StringBuilder();
			bdays.forEach((date, dateBdays) -> {
				message.append(date).append(": ");
				message.append(dateBdays.stream().map(Birthday::getName).collect(joining(", ")));
				message.append("\n");
			});
			sendMessage(chat, message.toString());
		}
	}

	private String monthName(int month) {
		return DateFormatSymbols.getInstance(new Locale("nl")).getMonths()[month - 1];
	}

	private LocalDate getNextOccurrence(int month, int day) {
		LocalDate today = date();
		LocalDate nextDate = today.withMonth(month).withDayOfMonth(day);
		if (nextDate.isBefore(today)) {
			nextDate = nextDate.plusYears(1);
		}
		return nextDate;
	}

	private <T> Map<T, Set<Birthday>> groupBy(Set<Birthday> bdays, Function<Birthday, T> group) {
		return bdays.stream().collect(groupingBy(group, TreeMap::new, mapping(identity(), toCollection(TreeSet::new))));
	}

}
