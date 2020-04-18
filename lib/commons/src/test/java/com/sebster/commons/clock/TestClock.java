package com.sebster.commons.clock;

import static lombok.AccessLevel.PRIVATE;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * A clock that can be used in tests. The main feature is that it can be advanced by the test using the {@link #sleep(Duration)}
 * method without actually having to wait.
 */
@ToString
@AllArgsConstructor(access = PRIVATE)
public class TestClock extends Clock {

	private final @NonNull Clock baseClock;
	private final AtomicReference<Duration> offset;

	@Override
	public ZoneId getZone() {
		return baseClock.getZone();
	}

	@Override
	public Clock withZone(@NonNull ZoneId zone) {
		return new TestClock(baseClock.withZone(zone), offset);
	}

	@Override
	public long millis() {
		return Math.addExact(baseClock.millis(), offset.get().toMillis());
	}

	@Override
	public Instant instant() {
		return baseClock.instant().plus(offset.get());
	}

	/**
	 * Pretend to sleep for the specified duration. This effectively advances the test clock by the specified amount immediately.
	 */
	public TestClock sleep(@NonNull Duration duration) {
		offset.set(offset.get().plus(duration));
		return this;
	}

	/**
	 * Pretend to sleep until the specified time. This effectively advances the test clock to at least the specified time
	 * immediately.
	 */
	public TestClock sleepUntil(@NonNull LocalTime localTime) {
		ZonedDateTime now = instant().atZone(getZone());
		ZonedDateTime target = now.with(localTime);
		if (target.isBefore(now)) {
			target = target.plusDays(1);
		}
		sleep(Duration.between(now, target));
		return this;
	}

	/**
	 * Create a new test clock with the fixed specified instant (and zone).
	 */
	public static TestClock fromInstant(@NonNull Instant now, @NonNull ZoneId zone) {
		return new TestClock(fixed(now, zone), new AtomicReference<>(Duration.ZERO));
	}

	/**
	 * Create a new test clock from the specified clock. It tracks the specified clock except for any offsets added by using {@link
	 * #sleep(Duration)} on the test clock.
	 */
	public static TestClock fromClock(@NonNull Clock clock) {
		return new TestClock(clock, new AtomicReference<>(Duration.ZERO));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TestClock) {
			TestClock other = (TestClock) obj;
			return baseClock.equals(other.baseClock) && offset.equals(other.offset);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(baseClock, offset.get());
	}

}

