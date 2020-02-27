package com.sebster.commons.clock.springboot;

import java.time.Clock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sebster.commons.clock.ClockService;

@Configuration
public class ClockAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

	@Bean
	public ClockService clockService(Clock clock) {
		return new ClockService(clock);
	}

}
