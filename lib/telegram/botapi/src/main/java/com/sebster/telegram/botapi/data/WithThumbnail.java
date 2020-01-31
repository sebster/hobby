package com.sebster.telegram.botapi.data;

import java.util.Optional;

public interface WithThumbnail {

	Optional<TelegramPhoto> getThumbnail();

	default boolean hasThumbnail() {
		return getThumbnail().isPresent();
	}

	interface TelegramThumbnailBuilder {

		TelegramThumbnailBuilder thumbnail(TelegramPhoto thumbnail);

		WithThumbnail build();

	}

}
