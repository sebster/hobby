package com.sebster.remarkable.cloudapi;

import lombok.NonNull;

public class RemarkableException extends RuntimeException {

	public RemarkableException(@NonNull String message) {
		super(message);
	}

}
