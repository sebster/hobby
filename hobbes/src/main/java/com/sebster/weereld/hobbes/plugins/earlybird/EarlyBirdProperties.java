package com.sebster.weereld.hobbes.plugins.earlybird;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "early-bird")
@Data
@NoArgsConstructor
public class EarlyBirdProperties {

	private List<Long> chatIds = new ArrayList<>();

}
