package com.sebster.weereld.hobbes.plugins.plato.subscription;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@ToString(doNotUseGetters = true)
public class PlatoSchedule {

	@Column(nullable = false)
	private long intervalLowerBound;

	@Column
	private Long intervalUpperBound;

	public Optional<Long> getIntervalUpperBound() {
		return Optional.ofNullable(intervalUpperBound);
	}

	public static PlatoSchedule platoSchedule(long intervalLowerBound, Long intervalUpperBound) {
		return new PlatoSchedule(intervalLowerBound, intervalUpperBound);
	}

}
