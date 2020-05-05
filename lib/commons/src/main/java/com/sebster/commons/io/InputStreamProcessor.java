package com.sebster.commons.io;

import java.io.IOException;
import java.io.InputStream;

import lombok.NonNull;

@FunctionalInterface
public interface InputStreamProcessor {

	void process(@NonNull InputStream inputStream) throws IOException;

}
