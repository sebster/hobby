package com.sebster.weereld.hobbes.plugins.plato;

import static java.time.Duration.ofDays;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "plato")
@Data
public class PlatoProperties {

	Duration unsolicitedQuotesIntervalLowerBound = ofDays(3);
	Duration unsolicitedQuotesIntervalUpperBound = ofDays(7);
	long unsolicitedQuotesChatId;

}
