package com.sebster.weereld.hobbes.people;

import static com.sebster.repository.api.properties.Property.optionalProperty;
import static com.sebster.repository.api.properties.Property.property;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.sebster.repository.api.properties.Property;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "nick")
@ToString
public class Person {

	@Id
	@Column
	@Getter
	private String nick;

	@Column
	private Integer telegramUserId;

	@Column
	private LocalDate birthDate;

	@Column
	private String zone;

	@Column
	private String city;

	@Column(length = 2)
	private String country;

	public Optional<Integer> getTelegramUserId() {
		return Optional.ofNullable(telegramUserId);
	}

	public Optional<LocalDate> getBirthDate() {
		return Optional.ofNullable(birthDate);
	}

	public Optional<ZoneId> getZone() {
		return Optional.ofNullable(zone).map(ZoneId::of);
	}

	// Properties

	public static final Property<Person, String> NICK =
			property(Person.class, "nick", Person::getNick);

	public static final Property<Person, Integer> TELEGRAM_USER_ID =
			optionalProperty(Person.class, "telegramUserId", Person::getTelegramUserId);

	public static final Property<Person, LocalDate> BIRTH_DATE =
			optionalProperty(Person.class, "birthDate", Person::getBirthDate);

}
