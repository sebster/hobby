package com.sebster.weereld.hobbes.utils;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static lombok.AccessLevel.PRIVATE;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor(access = PRIVATE)
public final class TimeUtils {

	private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

	public static void sleep(Duration duration) {
		try {
			MILLISECONDS.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			logger.warn("Sleep interrupted", e);
			currentThread().interrupt();
		}
	}

}
