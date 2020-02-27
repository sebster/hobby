package com.sebster.weereld.hobbes.plugins.earlybird;

import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@ToString
public class EarlyBirdId implements Serializable {

	@Column(nullable = false)
	private long chatId;

	@Column(nullable = false)
	private String nick;

	@Column(nullable = false)
	private LocalDate date;

}
