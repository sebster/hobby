package com.sebster.telegram.api.data;

import java.util.Optional;

public interface WithThumbnail {
	
	Optional<TelegramPhoto> getThumbnail();

}
