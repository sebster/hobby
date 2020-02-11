package com.sebster.repository.jpa.specifications;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = JpaSpecificationAutoConfiguration.class)
@EnableConfigurationProperties(JpaPredicateProperties.class)
public class JpaSpecificationAutoConfiguration {

	@Bean
	public JpaPredicateFactory jpaPredicateFactory(JpaPredicateProperties properties) {
		return new JpaPredicateFactory(properties.getLike().getCharsToEscape());
	}

}
