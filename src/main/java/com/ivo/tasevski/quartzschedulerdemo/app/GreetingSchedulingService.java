package com.ivo.tasevski.quartzschedulerdemo.app;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Managing Trigger and Job Creation
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GreetingSchedulingService {

    private final Scheduler scheduler;
    private final GreetingRepository greetingRepository;
    private final LoggingService loggingService;

    @Transactional
    public <T extends Job> void scheduleGreeting(CreateGreetingJobCmd cmd)
            throws SchedulerException {

        Greeting greeting = new Greeting()
                .setMessage("Greetings " + cmd.getFirstName() + "! This is a scheduled greeting by Quartz.");
        greetingRepository.save(greeting);

        Map<String, Object> params = new HashMap<>();
        params.put("uuid", greeting.getId());

        final JobDetail jobDetail = buildJobDetail(GreetingJob.class, params);
        final Trigger trigger = buildTrigger(jobDetail.getKey(), LocalTime.now().plusSeconds(cmd.getDelayInSeconds()));

        scheduler.scheduleJob(jobDetail, trigger);

        // this can be used to test behavior when exception is thrown
        loggingService.log(jobDetail, trigger);
    }

    public List<JobResponse> findAllScheduledJobs() throws SchedulerException {
        List<JobResponse> result = new ArrayList<>();
        var triggerKeys = scheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
        for (var triggerKey : triggerKeys) {
            var trigger = scheduler.getTrigger(triggerKey);
            result.add(JobResponse.builder()
                    .jobName(trigger.getJobKey().getName())
                    .groupName(trigger.getJobKey().getGroup())
                    .scheduleTime(trigger.getStartTime().toString())
                    .jobData(scheduler.getJobDetail(trigger.getJobKey()).getJobDataMap()).build());
        }
        return result;
    }

    public <T extends Job> JobDetail buildJobDetail(
            Class<? extends Job> job, Map<String, Object> params) {

        JobDataMap jobDataMap = new JobDataMap();

        if (params != null) {
            jobDataMap.putAll(params);
        }

        String jobName = UUID.randomUUID().toString();
        String jobGroup = "GREETING";

        return JobBuilder.newJob(job)
                .withIdentity(jobName, jobGroup)
                .withDescription("Scheduled greeting message")
                .usingJobData(jobDataMap)
                .storeDurably(false)
                .build();
    }

    private Trigger buildTrigger(JobKey jobKey, final LocalTime startTime) {

        String triggerName = UUID.randomUUID().toString();
        String triggerGroup = "ONE_TIME_GREETING_TRIGGER";

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroup)
                .forJob(jobKey)
                .startAt(localTimeToDate(startTime))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                .build();
        return trigger;
    }

    private Date localTimeToDate(final LocalTime startTime) {
        Instant instant = startTime.atDate(LocalDate
                        .of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()))
                .atZone(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }
}
