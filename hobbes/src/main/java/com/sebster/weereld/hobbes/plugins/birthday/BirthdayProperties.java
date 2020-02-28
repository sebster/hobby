package com.sebster.weereld.hobbes.plugins.birthday;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "birthday")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BirthdayProperties {

	private Long singChatId;

}
