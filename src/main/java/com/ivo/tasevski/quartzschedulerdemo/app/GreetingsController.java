package com.ivo.tasevski.quartzschedulerdemo.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class GreetingsController {
    private final GreetingSchedulingService greetingSchedulingService;


    @PostMapping("/jobs")
    public ResponseEntity<Void> createJob(@RequestBody CreateGreetingJobCmd cmd) throws Exception {

        greetingSchedulingService.scheduleGreeting(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponse>> findAllJob() throws Exception {
        return ResponseEntity.ok().body(greetingSchedulingService.findAllScheduledJobs());
    }
}
