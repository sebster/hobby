package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class RegistrationRequestDto {

	@JsonProperty("code")
	private String code;

	@JsonProperty("devicesDesc")
	private String clientType;

	@JsonProperty("deviceID")
	private String clientId;

}
