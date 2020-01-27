package com.sebster.weereld.hobbes.people;

import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PartnerId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String nick1;

	@Column(nullable = false)
	private String nick2;

}
