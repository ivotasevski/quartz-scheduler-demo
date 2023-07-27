package com.ivo.tasevski.quartzschedulerdemo.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class GreetingsController {
    private final GreetingSchedulingService greetingSchedulingService;


    @PostMapping("/jobs")
    public ResponseEntity<Void> scheduleGreeting(@RequestBody CreateGreetingJobCmd cmd)  {
        try {
            greetingSchedulingService.scheduleGreeting(cmd);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("Exception occurred.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponse>> findAllGreetingJobs() throws Exception {
        return ResponseEntity.ok().body(greetingSchedulingService.findAllScheduledJobs());
    }
}
