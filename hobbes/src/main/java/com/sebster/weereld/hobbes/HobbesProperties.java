package com.sebster.weereld.hobbes;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "hobbes")
@Data
public class HobbesProperties {

	private List<Integer> telegramFromWhiteList;
	private List<Long> telegramChatWhiteList;

}
