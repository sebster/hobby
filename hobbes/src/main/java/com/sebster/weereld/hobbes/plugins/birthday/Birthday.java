package com.sebster.weereld.hobbes.plugins.birthday;

import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Comparator.comparing;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.function.Predicate;

import com.sebster.weereld.hobbes.people.Partner;
import com.sebster.weereld.hobbes.people.Person;
import lombok.NonNull;
import lombok.Value;

@Value
public class Birthday implements Comparable<Birthday> {

	@NonNull String name;
	@NonNull LocalDate date;

	public int getDay() {
		return date.getDayOfMonth();
	}

	public int getMonth() {
		return date.getMonthValue();
	}

	public int getYear() {
		return date.getYear();
	}

	public int getAge(LocalDate date) {
		return Period.between(this.date, date).getYears();
	}

	@Override
	public int compareTo(@NonNull Birthday birthday) {
		return NATURAL_ORDER.compare(this, birthday);
	}

	public static Comparator<Birthday> NATURAL_ORDER =
			comparing(Birthday::getDate).thenComparing(Birthday::getName, CASE_INSENSITIVE_ORDER);

	public static Predicate<Birthday> withName(@NonNull String name) {
		return bday -> bday.getName().equalsIgnoreCase(name);
	}

	public static Predicate<Birthday> withNames(@NonNull String name1, @NonNull String name2) {
		return withName(name1 + " & " + name2).or(withName(name2 + " & " + name1));
	}

	public static Predicate<Birthday> withBirthdayOn(@NonNull LocalDate date) {
		return withMonth(date.getMonthValue()).and(withDay(date.getDayOfMonth())).and(isBornAfter(date));
	}

	public static Predicate<Birthday> isBornAfter(@NonNull LocalDate date) {
		return bday -> !date.isBefore(bday.getDate());
	}

	public static Predicate<Birthday> withDay(int day) {
		return bday -> bday.getDay() == day;
	}

	public static Predicate<Birthday> withMonth(int month) {
		return bday -> bday.getMonth() == month;
	}

	public static Predicate<Birthday> withYear(int year) {
		return bday -> bday.getYear() == year;
	}

	public static Birthday of(@NonNull Person person) {
		return new Birthday(
				person.getNick(),
				person.getBirthDate().orElseThrow(() -> new IllegalArgumentException("Birth date unknown: " + person))
		);
	}

	public static Birthday of(@NonNull Partner partner) {
		return new Birthday(
				partner.getNick1() + " & " + partner.getNick2(),
				partner.getDate().orElseThrow(() -> new IllegalArgumentException("Partner date unknown: " + partner))
		);
	}

}
