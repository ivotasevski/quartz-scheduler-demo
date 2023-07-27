package com.ivo.tasevski.quartzschedulerdemo.app;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class GlobalTriggerListener implements TriggerListener {

    @Override
    public String getName() {
        return "GlobalTriggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        final JobKey jobKey = trigger.getJobKey();
        log.info("triggerFired() at:{}, scheduledAt:{} :: jobKey : {}", new Date(), trigger.getStartTime(), jobKey);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        final JobKey jobKey = trigger.getJobKey();
        log.info("triggerMisfired() at:{}, scheduledAt:{} :: jobKey : {}", new Date(), trigger.getStartTime(), jobKey);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
                                Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        final JobKey jobKey = trigger.getJobKey();
        log.info("triggerComplete() at:{}, scheduledAt:{} :: jobKey : {}", new Date(), trigger.getStartTime(), jobKey);

    }
}
