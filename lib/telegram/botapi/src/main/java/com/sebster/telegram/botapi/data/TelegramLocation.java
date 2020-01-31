package com.sebster.telegram.botapi.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * This object represents a point on the map.
 */
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
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
