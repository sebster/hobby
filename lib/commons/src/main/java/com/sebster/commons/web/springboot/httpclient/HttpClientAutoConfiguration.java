package com.sebster.commons.web.springboot.httpclient;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = HttpClientAutoConfiguration.class)
public class HttpClientAutoConfiguration {

}
