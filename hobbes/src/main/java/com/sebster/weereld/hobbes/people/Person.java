package com.sebster.weereld.hobbes.people;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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

	public boolean hasBirthDate() {
		return birthDate != null;
	}

	public Optional<ZoneId> getZone() {
		return Optional.ofNullable(zone).map(ZoneId::of);
	}

}
