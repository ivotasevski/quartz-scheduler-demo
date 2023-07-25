package com.ivo.tasevski.quartzschedulerdemo.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;


/**
 * an interface to be implemented by components that you wish to have executed by the scheduler
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GreetingJob implements Job {

    private final ObjectMapper objectMapper;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        log.info("Greetings " + jobDataMap.get("firstName") + "! This was a scheduled greeting by Quartz.");
    }
}
