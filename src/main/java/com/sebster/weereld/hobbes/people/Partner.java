package com.sebster.weereld.hobbes.people;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@IdClass(PartnerId.class)
public class Partner {

	@Id
	private String nick1;

	@Id
	private String nick2;

	@Column
	private LocalDate date;

	public String nick1() {
		return nick1;
	}

	public String nick2() {
		return nick2;
	}

	public LocalDate date() {
		return date;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Partner) {
			Partner rhs = (Partner) obj;
			return new EqualsBuilder().append(nick1, rhs.nick1).append(nick2, rhs.nick2).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nick1).append(nick2).toHashCode();
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
