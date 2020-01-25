package com.sebster.telegram.impl.dto;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebster.telegram.api.data.TelegramLocation;

/**
 * This object represents a point on the map.
 */
public final class TelegramLocationDto {

	private final double longitude;
	private final double latitude;

	@JsonCreator
	public TelegramLocationDto(@JsonProperty("longitude") double longitude, @JsonProperty("latitude") double latitude) {
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

	public TelegramLocation toTelegramLocation() {
		return new TelegramLocation(longitude, latitude);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
