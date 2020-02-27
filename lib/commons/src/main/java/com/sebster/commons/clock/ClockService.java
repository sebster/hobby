package com.sebster.commons.clock;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ClockService {

	private final @NonNull Clock clock;

	public Clock clock() {
		return clock;
	}

	public LocalDate localDate() {
		return LocalDate.now(clock);
	}

	public LocalDate localDate(ZoneId zone) {
		return LocalDate.now(clock.withZone(zone));
	}

	public LocalTime localTime() {
		return LocalTime.now(clock);
	}

	public LocalTime localTime(ZoneId zone) {
		return LocalTime.now(clock.withZone(zone));
	}

	public LocalDateTime localDateTime() {
		return LocalDateTime.now(clock);
	}

	public LocalDateTime localDateTime(ZoneId zone) {
		return LocalDateTime.now(clock.withZone(zone));
	}

}
