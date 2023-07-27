package com.ivo.tasevski.quartzschedulerdemo;

import com.ivo.tasevski.quartzschedulerdemo.app.LoggingService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.spy;

@TestConfiguration
public class IntegrationTestConfig {
    @Bean
    @Primary
    public LoggingService spyLoggingService() {
        return spy(LoggingService.class);
    }
}
