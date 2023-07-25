package com.ivo.tasevski.quartzschedulerdemo.config;

import com.ivo.tasevski.quartzschedulerdemo.app.GlobalJobListener;
import com.ivo.tasevski.quartzschedulerdemo.app.GlobalTriggerListener;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer(GlobalJobListener globalJobListener, GlobalTriggerListener globalTriggerListener) {
        return schedulerFactoryBean -> {
          schedulerFactoryBean.setGlobalJobListeners(globalJobListener);
          schedulerFactoryBean.setGlobalTriggerListeners(globalTriggerListener);
        };
    }
}
