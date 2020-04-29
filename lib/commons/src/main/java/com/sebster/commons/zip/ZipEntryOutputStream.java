package com.sebster.commons.zip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

class ZipEntryOutputStream extends OutputStream {

	private final Zip zip;
	private final ZipOutputStream zipOutputStream;

	ZipEntryOutputStream(Zip zip, ZipOutputStream zipOutputStream) {
		this.zip = zip;
		this.zipOutputStream = zipOutputStream;
	}

	@Override
	public void write(int b) throws IOException {
		zipOutputStream.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		zipOutputStream.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		zipOutputStream.write(b, off, len);
	}

	@Override
	public void flush() throws IOException {
		zipOutputStream.flush();
	}

	@Override
	public void close() {
		zip.markEntryClosed();
	}

}
