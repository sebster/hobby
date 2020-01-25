package com.sebster.telegram.api.data;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This object represent a user's profile pictures.
 */
public final class TelegramUserProfilePhotos implements Serializable, Iterable<TelegramPhotoList> {

	private static final long serialVersionUID = 1L;

	private final int totalNumberOfPhotos;
	private final List<TelegramPhotoList> photos;

	public TelegramUserProfilePhotos(int totalNuberOfPhotos, List<TelegramPhotoList> photos) {
		this.totalNumberOfPhotos = totalNuberOfPhotos;
		this.photos = unmodifiableList(new ArrayList<>(requireNonNull(photos, "photos")));
	}

	/**
	 * Total number of profile pictures the target user has
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
