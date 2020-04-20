package com.sebster.remarkable.cloudapi;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class RemarkableClientInfo {

	@NonNull UUID clientId;
	@NonNull String loginToken;

}
