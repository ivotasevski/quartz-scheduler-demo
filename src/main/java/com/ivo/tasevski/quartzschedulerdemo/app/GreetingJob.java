package com.ivo.tasevski.quartzschedulerdemo.app;

import jakarta.persistence.EntityNotFoundException;
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

    private final GreetingRepository greetingRepository;

    @Override
    public void execute(JobExecutionContext context) {
        String uuid = context.getJobDetail().getJobDataMap().getString("uuid");
        var greeting = greetingRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("No Greeting with uuid = " + uuid));
        log.info(greeting.getMessage());
    }
}
