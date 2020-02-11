package com.sebster.repository.jpa.properties;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = JpaPropertyAutoConfiguration.class)
public class JpaPropertyAutoConfiguration {
}
