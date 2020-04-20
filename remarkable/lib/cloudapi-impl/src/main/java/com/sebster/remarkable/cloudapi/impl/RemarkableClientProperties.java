package com.sebster.remarkable.cloudapi.impl;

import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "remarkable")
@Data
public class RemarkableClientProperties {

	private UUID clientId;
	private String loginToken;

}
