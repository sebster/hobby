package com.sebster.telegram.api.data.messages;

import java.util.Optional;

public interface WithCaption {

	/**
	 * Optional. Caption for the photo or video
	 */
	Optional<String> getCaption();

}
