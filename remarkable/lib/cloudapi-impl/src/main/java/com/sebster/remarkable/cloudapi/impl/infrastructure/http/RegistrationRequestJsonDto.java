package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
class RegistrationRequestJsonDto {

	@JsonProperty("code")
	String code;
	@JsonProperty("DeviceDesc")
	String clientType;
	@JsonProperty("DeviceID")
	String clientId;

}
