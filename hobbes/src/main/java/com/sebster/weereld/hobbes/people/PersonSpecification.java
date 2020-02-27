package com.sebster.weereld.hobbes.people;

import static com.sebster.repository.api.properties.specifications.PropertySpecification.eq;
import static com.sebster.repository.api.properties.specifications.PropertySpecification.eqIgnoreCase;
import static com.sebster.repository.api.properties.specifications.PropertySpecification.isNotNull;
import static com.sebster.weereld.hobbes.people.Person.BIRTH_DATE;
import static com.sebster.weereld.hobbes.people.Person.NICK;
import static com.sebster.weereld.hobbes.people.Person.TELEGRAM_USER_ID;
import static com.sebster.weereld.hobbes.people.Person.ZONE;

import com.sebster.repository.api.specifications.Specification;
import lombok.NonNull;

public interface PersonSpecification extends Specification<Person> {

	static Specification<Person> withNick(@NonNull String nick) {
		return eqIgnoreCase(NICK, nick);
	}

	static Specification<Person> withTelegramUserId(int telegramUserId) {
		return eq(TELEGRAM_USER_ID, telegramUserId);
	}

	static Specification<Person> hasTelegramUserId() {
		return isNotNull(TELEGRAM_USER_ID);
	}

	static Specification<Person> hasZone() {
		return isNotNull(ZONE);
	}

	static Specification<Person> hasBirthDate() {
		return isNotNull(BIRTH_DATE);
	}

}
