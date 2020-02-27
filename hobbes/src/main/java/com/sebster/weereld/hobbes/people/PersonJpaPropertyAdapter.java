package com.sebster.weereld.hobbes.people;

import static com.sebster.weereld.hobbes.people.Person.BIRTH_DATE;
import static com.sebster.weereld.hobbes.people.Person.NICK;
import static com.sebster.weereld.hobbes.people.Person.TELEGRAM_USER_ID;
import static com.sebster.weereld.hobbes.people.Person.ZONE;
import static com.sebster.weereld.hobbes.people.Person_.birthDate;
import static com.sebster.weereld.hobbes.people.Person_.nick;
import static com.sebster.weereld.hobbes.people.Person_.telegramUserId;
import static com.sebster.weereld.hobbes.people.Person_.zone;

import java.time.ZoneId;

import org.springframework.stereotype.Component;

import com.sebster.repository.jpa.properties.AbstractJpaPropertyAdapter;

@Component
public class PersonJpaPropertyAdapter extends AbstractJpaPropertyAdapter<Person> {

	public PersonJpaPropertyAdapter() {
		map(NICK, nick);
		map(TELEGRAM_USER_ID, telegramUserId);
		map(BIRTH_DATE, birthDate);
		map(ZONE.transformNullSafe(ZoneId::getId, ZoneId::of), zone);
	}

}
