package com.advancedScheduler.springConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.*")
@PropertySource("classpath:/spring/input.xml")
public class AppConfig {

}
