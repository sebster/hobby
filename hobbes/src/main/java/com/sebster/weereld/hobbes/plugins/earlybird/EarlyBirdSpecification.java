package com.sebster.weereld.hobbes.plugins.earlybird;

import static com.sebster.repository.api.properties.specifications.PropertySpecification.eq;
import static com.sebster.repository.api.properties.specifications.PropertySpecification.eqIgnoreCase;
import static com.sebster.repository.api.properties.specifications.PropertySpecification.isTrue;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.DATE;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.NICK;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.WINNER;

import java.time.LocalDate;

import com.sebster.repository.api.specifications.Specification;
import lombok.NonNull;

public interface EarlyBirdSpecification extends Specification<EarlyBird> {

	static Specification<EarlyBird> withNick(@NonNull String nick) {
		return eqIgnoreCase(NICK, nick);
	}

	static Specification<EarlyBird> withDate(@NonNull LocalDate date) {
		return eq(DATE, date);
	}

	static Specification<EarlyBird> isWinner() {
		return isTrue(WINNER);
	}

}
