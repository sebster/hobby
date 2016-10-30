package com.sebster.weereld.hobbes.people;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class PartnerId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String nick1;

	@Column(nullable = false)
	private String nick2;

	protected PartnerId() {
	}

	public PartnerId(String nick1, String nick2) {
		this.nick1 = nick1;
		this.nick2 = nick2;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this instanceof PartnerId) {
			PartnerId rhs = (PartnerId) obj;
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
