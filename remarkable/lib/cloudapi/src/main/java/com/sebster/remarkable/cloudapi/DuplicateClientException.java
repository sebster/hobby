package com.sebster.remarkable.cloudapi;

import lombok.NonNull;

public class DuplicateClientException extends RemarkableException {

	public DuplicateClientException(@NonNull String message) {
		super(message);
	}

}
