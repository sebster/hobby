package com.sebster.weereld.hobbes.plugins.wuvwav;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "wuvwav")
@Data
public class WuvWavProperties {

	private BigDecimal wuvMarkup = new BigDecimal("2.0");
	private BigDecimal wavMarkup = new BigDecimal("3.0");
	private BigDecimal mortgageSebster = new BigDecimal("100000");

}
