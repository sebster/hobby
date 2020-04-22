package com.sebster.remarkable.cloudapi.impl.controller;

import java.util.UUID;

import com.sebster.remarkable.cloudapi.RemarkableClientInfo;
import lombok.NonNull;
import lombok.Value;

@Value
public class RemarkableClientDescriptor {

	@NonNull UUID clientId;
	@NonNull String description;
	@NonNull String loginToken;

	public RemarkableClientInfo toInfo() {
		return new RemarkableClientInfo(clientId, description);
	}

}
