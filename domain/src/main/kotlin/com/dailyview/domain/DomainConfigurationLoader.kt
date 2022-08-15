package com.dailyview.domain

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@ComponentScan
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableAutoConfiguration
class DomainConfigurationLoader
