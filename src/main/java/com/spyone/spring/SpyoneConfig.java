package com.spyone.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.spyone.gui", "com.spyone.model.profiles.da"})
public class SpyoneConfig {}
