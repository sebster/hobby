package com.sebster.weereld.hobbes.plugins.birthday;

import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Comparator.comparing;

import java.time.LocalDate;
import java.time.Period;

public class Birthday implements Comparable<Birthday> {

	private final String name;
	private final LocalDate date;

	public Birthday(String name, LocalDate date) {
		this.name = name;
		this.date = date;
	}

	public String name() {
		return name;
	}

	public LocalDate date() {
		return date;
	}

	public int day() {
		return date.getDayOfMonth();
	}

	public int month() {
		return date.getMonthValue();
	}

	public int year() {
		return date.getYear();
	}

	public int age(LocalDate date) {
		return Period.between(this.date, date).getYears();
	}

	@Override
	public int compareTo(Birthday other) {
		return comparing(Birthday::date).thenComparing(Birthday::name, CASE_INSENSITIVE_ORDER).compare(this, other);
	}

}
