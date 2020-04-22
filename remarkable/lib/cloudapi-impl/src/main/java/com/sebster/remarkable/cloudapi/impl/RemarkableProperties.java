package com.sebster.remarkable.cloudapi.impl;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "remarkable")
@Data
public class RemarkableProperties {

	private File clients = new File(System.getProperty("user.home") + "/.remarkable-clients.json");

}
