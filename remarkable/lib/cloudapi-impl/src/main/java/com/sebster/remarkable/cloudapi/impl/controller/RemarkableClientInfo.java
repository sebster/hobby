package com.sebster.remarkable.cloudapi.impl.controller;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class RemarkableClientInfo {

	@NonNull UUID clientId;
	@NonNull String description;
	@NonNull String loginToken;

}
