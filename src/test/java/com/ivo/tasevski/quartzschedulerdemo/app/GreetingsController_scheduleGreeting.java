package com.ivo.tasevski.quartzschedulerdemo.app;


import org.junit.jupiter.api.Test;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GreetingsController_scheduleGreeting extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GreetingRepository greetingRepository;


    private String REQUEST_CONTENT =
            """
                    {
                      "firstName": "John",
                      "delayInSeconds": "60"                      
                    }
                    """;

    @Test
    void shouldScheduleGreeting() throws Exception {
        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_CONTENT))
                .andExpect(status().isCreated());

        assertThat(greetingRepository.findAll().size()).isEqualTo(1);
        assertThat(scheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()).size()).isEqualTo(1);
    }

    @Test
    void shouldNotScheduleGreeting_whenExceptionOccursWithinTransaction() throws Exception {

        // throw exception to cause the transaction to fail
        doThrow(RuntimeException.class).when(loggingService).log(any(), any());

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_CONTENT))
                .andExpect(status().isInternalServerError());

        // no saved greeting job metadata nor scheduled triggers
        assertThat(greetingRepository.findAll().isEmpty()).isTrue();
        assertThat(scheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()).isEmpty()).isTrue();
    }
}
