package com.sebster.weereld.hobbes.plugins.earlybird;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class EarlyBirdId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String nick;

	@Column(nullable = false)
	private LocalDate date;

	protected EarlyBirdId() {
	}

	public EarlyBirdId(String nick, LocalDate date) {
		this.nick = nick;
		this.date = date;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this instanceof EarlyBirdId) {
			EarlyBirdId rhs = (EarlyBirdId) obj;
			return new EqualsBuilder().append(nick, rhs.nick).append(date, rhs.date).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nick).append(date).toHashCode();
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
