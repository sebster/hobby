package com.sebster.telegram.api.data;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;

/**
 * This object represents a point on the map.
 */
public final class TelegramLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private final double longitude;
	private final double latitude;

	public TelegramLocation(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * Longitude as defined by sender
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Latitude as defined by sender
	 */
	public double getLatitude() {
		return latitude;
	}

	@Override
	public final boolean equals(Object obj) {
		return reflectionEquals(this, obj, false);
	}

	@Override
	public final int hashCode() {
		return reflectionHashCode(this, false);
	}

	@Override
	public final String toString() {
		return reflectionToString(this);
	}

}
