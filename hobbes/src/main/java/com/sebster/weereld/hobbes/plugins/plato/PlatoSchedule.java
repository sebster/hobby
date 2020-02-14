package com.sebster.weereld.hobbes.plugins.plato;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class PlatoSchedule {

	@Column(nullable = false)
	private long intervalLowerBound;

	@Column
	private long intervalUpperBound;

	static PlatoSchedule platoSchedule(long intervalLowerBound, long intervalUpperBound) {
		return new PlatoSchedule(intervalLowerBound, intervalUpperBound);
	}

}
