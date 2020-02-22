package com.sebster.weereld.hobbes.plugins.plato;

import static java.time.Duration.ofDays;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.sebster.weereld.hobbes.plugins.plato.subscription.PlatoDefaultSchedulingIntervalProvider;
import lombok.Data;

@ConfigurationProperties(prefix = "plato")
@Data
public class PlatoProperties implements PlatoDefaultSchedulingIntervalProvider {

	Duration intervalLowerBound = ofDays(3);
	Duration intervalUpperBound = ofDays(7);
	long unsolicitedQuotesChatId;

}
