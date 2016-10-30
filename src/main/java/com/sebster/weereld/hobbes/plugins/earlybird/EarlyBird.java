package com.sebster.weereld.hobbes.plugins.earlybird;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@IdClass(EarlyBirdId.class)
public class EarlyBird {

	@Id
	private String nick;

	@Id
	private LocalDate date;

	@Column(nullable = false)
	private LocalTime wakeUpTime;
	
	@Column(nullable = false)
	private boolean winner;

	protected EarlyBird() {
	}

	public EarlyBird(String nick, LocalDate date, LocalTime wakeUpTime) {
		this.nick = nick;
		this.date = date;
		this.wakeUpTime = wakeUpTime;
		this.winner = false;
	}

	public String nick() {
		return nick;
	}

	public LocalDate date() {
		return date;
	}

	public LocalTime wakeUpTime() {
		return wakeUpTime;
	}

	public void markWinner() {
		this.winner = true;
	}
	
	public boolean isWinner() {
		return winner;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this instanceof EarlyBird) {
			EarlyBird rhs = (EarlyBird) obj;
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
