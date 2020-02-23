package com.sebster.weereld.hobbes.utils;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static lombok.AccessLevel.PRIVATE;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor(access = PRIVATE)
@Slf4j
public final class TimeUtils {

	public static void sleep(Duration duration) {
		try {
			MILLISECONDS.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			log.warn("Sleep interrupted", e);
			currentThread().interrupt();
		}
	}

}
