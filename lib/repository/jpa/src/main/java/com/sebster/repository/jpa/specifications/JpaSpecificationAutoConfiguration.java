package com.sebster.repository.jpa.specifications;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = JpaSpecificationAutoConfiguration.class)
public class JpaSpecificationAutoConfiguration {
}
