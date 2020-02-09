package com.sebster.repository.jpa.properties.specifications;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = JpaPropertySpecificationAutoConfiguration.class)
public class JpaPropertySpecificationAutoConfiguration {
}
