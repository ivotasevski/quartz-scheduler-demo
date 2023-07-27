package com.ivo.tasevski.quartzschedulerdemo.app;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class LoggingService {

    public void log(JobDetail jobDetail, Trigger trigger) {
        log.info("Job {} has been scheduled for {}. Now is {}", jobDetail.getKey(), trigger.getStartTime(), new Date());
    }
}
