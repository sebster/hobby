package com.sebster.weereld.hobbes.plugins.earlybird;

import static com.sebster.repository.api.properties.specifications.PropertySpecification.eq;
import static com.sebster.repository.api.properties.specifications.PropertySpecification.eqIgnoreCase;
import static com.sebster.repository.api.properties.specifications.PropertySpecification.isTrue;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.CHAT_ID;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.DATE;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.NICK;
import static com.sebster.weereld.hobbes.plugins.earlybird.EarlyBird.WINNER;

import java.time.LocalDate;

import com.sebster.repository.api.specifications.Specification;
import lombok.NonNull;

public interface EarlyBirdSpecification extends Specification<EarlyBird> {

	static Specification<EarlyBird> forNickOnDate(@NonNull LocalDate date, @NonNull String nick, long chatId) {
		return onDate(date, chatId).and(withNick(nick));
	}

	static Specification<EarlyBird> onDate(@NonNull LocalDate date, long chatId) {
		return withChatId(chatId).and(withDate(date));
	}

	static Specification<EarlyBird> isWinner(long chatId) {
		return withChatId(chatId).and(isWinner());
	}

	static Specification<EarlyBird> withChatId(long chatId) {
		return eq(CHAT_ID, chatId);
	}

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
