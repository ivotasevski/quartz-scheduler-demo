package com.ivo.tasevski.quartzschedulerdemo.app;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Builder
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class JobResponse {
    private final String jobName;
    private final String groupName;
    private final String scheduleTime;
    private final Map<String, Object> jobData;
}
