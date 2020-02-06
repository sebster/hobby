package com.sebster.telegram.botapi.data;

import static java.util.List.copyOf;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.Validate;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This object represent a user's profile pictures.
 */
@Value
@Builder(toBuilder = true)
public class TelegramUserProfilePhotos implements Iterable<TelegramPhotoList> {

	int totalNumberOfPhotos;
	@NonNull List<TelegramPhotoList> photos;

	public TelegramUserProfilePhotos(int totalNumberOfPhotos, @NonNull List<TelegramPhotoList> photos) {
		this.totalNumberOfPhotos = totalNumberOfPhotos;
		Validate.noNullElements(photos, "photos[%d] must not be null");
		this.photos = copyOf(photos);
	}

	/**
	 * Total number of profile pictures the target user has.
	 */
	public int getTotalNumberOfPhotos() {
		return totalNumberOfPhotos;
	}

	/**
	 * Requested profile pictures (in up to 4 sizes each)
	 */
	public List<TelegramPhotoList> getPhotos() {
		return photos;
	}

	@Override
	public Iterator<TelegramPhotoList> iterator() {
		return photos.iterator();
	}

}
