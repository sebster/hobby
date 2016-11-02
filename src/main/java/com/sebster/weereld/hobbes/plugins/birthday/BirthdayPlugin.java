package com.sebster.weereld.hobbes.plugins.birthday;

import static com.sebster.weereld.hobbes.utils.StringUtils.formatIfPresent;
import static com.sebster.weereld.hobbes.utils.TimeUtils.sleep;
import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.join;

import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
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

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.messages.TelegramTextMessage;
import com.sebster.weereld.hobbes.plugins.api.BasePlugin;

@Component
public class BirthdayPlugin extends BasePlugin {

	private static final Pattern BDAY_TODAY_PATTERN = compile("(?i)^bday$");
	private static final Pattern BDAY_FOR_NAME_PATTERN = compile("(?i)^bday ([a-z &]+)$");
	private static final Pattern BDAY_FOR_DATE_PATTERN = compile("(?i)^bday (\\d{4}-\\d{2}-\\d{2})$");
	private static final Pattern BDAY_FOR_MONTH_DAY_PATTERN = compile("(?i)^bday (\\d{2})-(\\d{2})$");
	private static final Pattern BDAY_MONTH_PATTERN = compile("(?i)^bday (\\d{1,2})$");
	private static final Pattern BDAY_YEAR_PATTERN = compile("(?i)^bday (\\d{4})$");
	private static final Pattern BDAY_HELP_PATTERN = compile("(?i)^bday help$");

	@Value("${birthday.sing.chatId}")
	private long singChatId;

	@Autowired
	private BirthdayRepository birthdayRepository;

	@Scheduled(cron = "0 0 0 * * *")
	public void sing() {
		LocalDate today = date();
		Set<Birthday> bdays = birthdayRepository.findByDate(today);
		if (!bdays.isEmpty()) {
			sendMessage(singChatId, "ER IS ER EEN JARIG!");
			sleep(Duration.ofSeconds(1));
			sendMessage(singChatId, "HOERA HOERA!");
			sleep(Duration.ofSeconds(1));
			sendMessage(singChatId, "DAT KUN JE WEL ZIEN DAT %s: ", bdays.size() == 1 ? "IS" : "ZIJN");
			sleep(Duration.ofSeconds(1));
			StringBuilder message = new StringBuilder();
			for (Birthday bday : bdays) {
				message.append(format("%s, die is vandaag %d jaar geworden!\n", bday.name(), bday.age(today)));
			}
			sendMessage(singChatId, message.toString());
		}
	}

	@Override
	public void visitTextMessage(TelegramTextMessage textMessage) {
		TelegramChat chat = textMessage.getChat();
		String from = getFirstName(textMessage.getFrom()).orElse(null);
		String text = textMessage.getText().trim();

		Matcher matcher;

		matcher = BDAY_HELP_PATTERN.matcher(text);
		if (matcher.matches()) {
			showHelp(chat);
			return;
		}

		matcher = BDAY_TODAY_PATTERN.matcher(text);
		if (matcher.matches()) {
			showBirthdaysToday(chat);
			return;
		}

		matcher = BDAY_FOR_NAME_PATTERN.matcher(text);
		if (matcher.matches()) {
			String name = matcher.group(1);
			showBirthdayForName(from, chat, name);
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
		Set<Birthday> birthdays = birthdayRepository.findByMonthAndDay(today.getMonthValue(), today.getDayOfMonth());
		if (birthdays.isEmpty()) {
			sendMessage(chat, "Ik ken niemand die vandaag jarig is!");
		} else {
			StringBuilder message = new StringBuilder();
			for (Birthday bday : birthdays) {
				message.append(format("%s is vandaag jarig en %d jaar geworden! :cake:", bday.name(), bday.age(today)));
			}
			sendMessage(chat, message.toString());
		}
	}

	private void showBirthdayForName(String from, TelegramChat chat, String name) {
		Optional<Birthday> bdayOpt = birthdayRepository.findByName(name);
		if (!bdayOpt.isPresent()) {
			sendMessage(chat, "Ik ken helemaal geen %s" + formatIfNotNull(from, ", %s") + "!", name);
		} else {
			Birthday bday = bdayOpt.get();
			sendMessage(chat, "%s is geboren op %s en is %d jaar oud.", bday.name(), bday.date(), bday.age(date()));
		}
	}

	private void showBirthdaysForDate(TelegramChat chat, LocalDate date) {
		Set<Birthday> bdays = birthdayRepository.findByDate(date);
		if (bdays.isEmpty()) {
			sendMessage(chat, "Ik ken niemand die op %s jarig %s!", date, date.isBefore(date()) ? "was" : "is");
		} else {
			StringBuilder message = new StringBuilder();
			for (Birthday bday : bdays) {
				int age = bday.age(date);
				if (age == 0) {
					message.append(format("%s is op %s geboren!\n", bday.name(), date));
				} else {
					String verb = date.isBefore(date()) ? "werdt" : "wordt";
					message.append(format("%s %s op %s maar liefst %d jaar oud!\n", bday.name(), verb, date, age));
				}
			}
			sendMessage(chat, message.toString());
		}
	}

	private void showBirthdaysForMonthDay(TelegramChat chat, int month, int day) {
		LocalDate today = date();
		LocalDate date = today.withMonth(month).withDayOfMonth(day);
		showBirthdaysForDate(chat, date.isBefore(today) ? date.plusYears(1) : date);
	}

	private void showBirthdaysForMonth(String from, TelegramChat chat, int month) {
		Validate.inclusiveBetween(1, 12, month);
		Map<Integer, Set<Birthday>> bdays = groupBy(birthdayRepository.findByMonth(month), Birthday::day);
		if (bdays.isEmpty()) {
			sendMessage(chat, "Ik ken niemand die in %s jarig is" + formatIfNotNull(from, ", %s") + "!", monthName(month));
		} else {
			StringBuilder message = new StringBuilder();
			bdays.forEach((day, dayBdays) -> {
				message.append(format("%02d-%02d: ", month, day));
				List<String> bdayStrings = dayBdays.stream()
						.map(bday -> format("%s (%d)", bday.name(), bday.year()))
						.collect(toList());
				message.append(join(bdayStrings, ", "));
				message.append("\n");
			});
			sendMessage(chat, message.toString());
		}
	}

	private void showBirthdaysForYear(TelegramChat chat, int year) {
		Map<LocalDate, Set<Birthday>> bdays = groupBy(birthdayRepository.findByYear(year), Birthday::date);
		if (bdays.isEmpty()) {
			sendMessage(chat, "Ik ken niemand die in %d geboren is!", year);
		} else {
			StringBuilder message = new StringBuilder();
			bdays.forEach((date, dateBdays) -> {
				message.append(date).append(": ");
				List<String> bdayStrings = dateBdays.stream().map(Birthday::name).collect(toList());
				message.append(join(bdayStrings, ", "));
				message.append("\n");
			});
			sendMessage(chat, message.toString());
		}
	}

	private void showHelp(TelegramChat chat) {
		// @formatter:off
		sendMessage(chat,
			"bday - wie is er vandaag jarig?\n" +
			"bday <naam> - wanneer is <naam> geboren?\n" +
			"bday <jjjj-mm-dd> - wie is er op <jjjj-mm-dd> jarig?\n" +
			"bday <mm-dd> - wie is er op <mm-dd> jarig?\n" +
			"bday <maand> - wie is er in <maand> jarig?\n" +
			"bday <jaar> - wie is in <jaar> geboren?\n" +
			"bday help - hoe werkt deze plugin?"
		);
		// @formatter:on
	}

	private String monthName(int month) {
		return DateFormatSymbols.getInstance(new Locale("nl")).getMonths()[month - 1];
	}

	private <T> Map<T, Set<Birthday>> groupBy(Set<Birthday> bdays, Function<Birthday, T> group) {
		return bdays.stream().collect(groupingBy(group, TreeMap::new, mapping(identity(), toCollection(TreeSet::new))));
	}

}
