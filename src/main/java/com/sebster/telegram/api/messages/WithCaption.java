package com.sebster.telegram.api.messages;

import java.util.Optional;

public interface WithCaption {

	/**
	 * Optional. Caption for the photo or video
	 */
	Optional<String> getCaption();

}
