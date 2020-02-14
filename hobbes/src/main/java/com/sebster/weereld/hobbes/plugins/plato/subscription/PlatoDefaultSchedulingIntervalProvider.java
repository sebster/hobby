package com.sebster.weereld.hobbes.plugins.plato.subscription;

import java.time.Duration;

public interface PlatoDefaultSchedulingIntervalProvider {

	Duration getIntervalLowerBound();

	Duration getIntervalUpperBound();

}
