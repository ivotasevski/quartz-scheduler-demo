package com.ivo.tasevski.quartzschedulerdemo.app;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateGreetingJobCmd {

    @NotBlank
    private String firstName;

    @NotBlank
    private Long delayInSeconds;
}
