package com.sebster.weereld.hobbes.people;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Person {

	@Id
	@Column
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

	public String nick() {
		return nick;
	}

	public Optional<Integer> telegramUserId() {
		return Optional.ofNullable(telegramUserId);
	}

	public Optional<LocalDate> birthDate() {
		return Optional.ofNullable(birthDate);
	}

	public Optional<ZoneId> zone() {
		return Optional.ofNullable(zone).map(ZoneId::of);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Person) {
			Person rhs = (Person) obj;
			return new EqualsBuilder().append(nick, rhs.nick).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nick).toHashCode();
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
