package com.sebster.telegram.api.data;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This object represents a photo or a file / sticker thumbnail. The photo
 * consists one or more sizes.
 */
public class TelegramPhotoList implements Serializable, Iterable<TelegramPhoto> {

	private static final long serialVersionUID = 1L;

	private final List<TelegramPhoto> photos;

	public TelegramPhotoList(List<TelegramPhoto> photos) {
		this.photos = new ArrayList<>(requireNonNull(photos, "photos"));
	}

	public int getNumberOfSizes() {
		return photos.size();
	}

	public TelegramPhoto getPhoto(int i) {
		return photos.get(i);
	}

	@Override
	public Iterator<TelegramPhoto> iterator() {
		return unmodifiableList(photos).iterator();
	}

	@Override
	public final boolean equals(Object obj) {
		return reflectionEquals(this, obj, false);
	}

	@Override
	public final int hashCode() {
		return reflectionHashCode(this, false);
	}

	@Override
	public final String toString() {
		return reflectionToString(this);
	}

}
