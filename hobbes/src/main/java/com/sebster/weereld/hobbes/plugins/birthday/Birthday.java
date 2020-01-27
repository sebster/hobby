package com.sebster.weereld.hobbes.plugins.birthday;

import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.time.LocalDate;
import java.time.Period;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Birthday implements Comparable<Birthday> {

	private final String name;
	private final LocalDate date;

	public Birthday(String name, LocalDate date) {
		this.name = requireNonNull(name, "name");
		this.date = requireNonNull(date, "date");
	}

	public String name() {
		return name;
	}

	public boolean hasName(String name) {
		return this.name.equalsIgnoreCase(name);
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
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Birthday) {
			Birthday rhs = (Birthday) obj;
			return equalsIgnoreCase(name, rhs.name);
		}
		return false;
	}

	@Override
	public int compareTo(Birthday other) {
		return comparing(Birthday::date).thenComparing(Birthday::name, CASE_INSENSITIVE_ORDER).compare(this, other);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name.toLowerCase()).hashCode();
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
