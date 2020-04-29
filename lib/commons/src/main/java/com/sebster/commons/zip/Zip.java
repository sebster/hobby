package com.sebster.commons.zip;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.NonNull;

/**
 * The Zip class wraps an output stream to allow streaming creation of a zip file.
 * <p>
 * To add entries to the zip, call {@link #newEntry}, write the entry to the returned output stream, and close
 * the output stream. Do this for each entry you want to add.
 * <p>
 * After writing all the entries, the Zip instance must be closed.
 */
public class Zip implements AutoCloseable {

	private final ZipOutputStream zipOutputStream;
	private boolean hasOpenEntry = false;

	public Zip(@NonNull OutputStream outputStream) {
		this.zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
	}

	public OutputStream newEntry(@NonNull String filename) {
		if (hasOpenEntry) {
			throw new IllegalStateException("previous entry has not been closed yet");
		}
		try {
			hasOpenEntry = true;
			zipOutputStream.putNextEntry(new ZipEntry(filename));
			return new ZipEntryOutputStream(this, zipOutputStream);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void close() {
		try {
			zipOutputStream.close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	void markEntryClosed() {
		this.hasOpenEntry = false;
	}

}
