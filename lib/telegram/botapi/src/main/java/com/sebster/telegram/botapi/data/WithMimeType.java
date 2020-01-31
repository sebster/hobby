package com.sebster.telegram.botapi.data;

import java.util.Optional;

public interface WithMimeType {

	Optional<String> getMimeType();

	default boolean hasMimeType() {
		return getMimeType().isPresent();
	}

	interface TelegramMimeTypeBuilder {

		TelegramMimeTypeBuilder mimeType(String mimeType);

		WithMimeType build();

	}

}
