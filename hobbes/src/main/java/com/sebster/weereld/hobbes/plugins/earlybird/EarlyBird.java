package com.sebster.weereld.hobbes.plugins.earlybird;

import static com.sebster.repository.api.properties.Property.property;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.sebster.repository.api.properties.Property;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@IdClass(EarlyBirdId.class)
@NoArgsConstructor(access = PROTECTED)
@Getter
@EqualsAndHashCode(of = { "chatId", "nick", "date" })
public class EarlyBird {

	@Id
	private long chatId;

	@Id
	private String nick;

	@Id
	private LocalDate date;

	@Column(nullable = false)
	private LocalTime wakeUpTime;

	@Column(nullable = false)
	private boolean winner;

	public EarlyBird(long chatId, @NonNull String nick, @NonNull LocalDate date, @NonNull LocalTime wakeUpTime) {
		this.chatId = chatId;
		this.nick = nick;
		this.date = date;
		this.wakeUpTime = wakeUpTime;
		this.winner = false;
	}

	public void markWinner() {
		this.winner = true;
	}

	public static final Property<EarlyBird, Long> CHAT_ID =
			property(EarlyBird.class, "chatId", EarlyBird::getChatId);
	public static final Property<EarlyBird, String> NICK =
			property(EarlyBird.class, "nick", EarlyBird::getNick);
	public static final Property<EarlyBird, LocalDate> DATE =
			property(EarlyBird.class, "date", EarlyBird::getDate);
	public static final Property<EarlyBird, LocalTime> WAKE_UP_TIME =
			property(EarlyBird.class, "wakeUpTime", EarlyBird::getWakeUpTime);
	public static final Property<EarlyBird, Boolean> WINNER =
			property(EarlyBird.class, "winner", EarlyBird::isWinner);

}
