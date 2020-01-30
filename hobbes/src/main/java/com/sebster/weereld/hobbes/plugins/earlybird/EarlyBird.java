package com.sebster.weereld.hobbes.plugins.earlybird;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@IdClass(EarlyBirdId.class)
@NoArgsConstructor(access = PROTECTED)
@Getter
@EqualsAndHashCode(of = { "nick", "date" })
public class EarlyBird {

	@Id
	private String nick;

	@Id
	private LocalDate date;

	@Column(nullable = false)
	private LocalTime wakeUpTime;

	@Column(nullable = false)
	private boolean winner;

	public EarlyBird(@NonNull String nick, @NonNull LocalDate date, @NonNull LocalTime wakeUpTime) {
		this.nick = nick;
		this.date = date;
		this.wakeUpTime = wakeUpTime;
		this.winner = false;
	}

	public void markWinner() {
		this.winner = true;
	}

}
