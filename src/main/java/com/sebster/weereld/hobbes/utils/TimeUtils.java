package com.sebster.weereld.hobbes.utils;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TimeUtils {

	private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

	private TimeUtils() {
	}

	public static void sleep(Duration duration) {
		try {
			MILLISECONDS.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			logger.warn("Sleep interrupted", e);
		}
	}

}
