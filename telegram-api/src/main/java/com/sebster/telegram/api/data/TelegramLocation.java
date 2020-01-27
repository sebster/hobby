package com.sebster.telegram.api.data;

import lombok.Value;

/**
 * This object represents a point on the map.
 */
@Value
public class TelegramLocation {

	double longitude;
	double latitude;

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

}
