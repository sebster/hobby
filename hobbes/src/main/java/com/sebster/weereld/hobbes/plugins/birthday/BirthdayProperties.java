package com.sebster.weereld.hobbes.plugins.birthday;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "birthday")
@Data
public class BirthdayProperties {

	private Long singChatId;

}
