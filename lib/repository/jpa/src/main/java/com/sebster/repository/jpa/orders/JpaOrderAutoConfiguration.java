package com.sebster.repository.jpa.orders;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = JpaOrderAutoConfiguration.class)
public class JpaOrderAutoConfiguration {
}
