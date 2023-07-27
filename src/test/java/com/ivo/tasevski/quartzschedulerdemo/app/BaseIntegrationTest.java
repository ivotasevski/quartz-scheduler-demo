package com.ivo.tasevski.quartzschedulerdemo.app;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.ivo.tasevski.quartzschedulerdemo.IntegrationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest
@TestExecutionListeners(
        listeners = DbUnitTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@Import(IntegrationTestConfig.class)
@AutoConfigureMockMvc
@DatabaseTearDown("classpath:clear-data.xml")
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Autowired
    protected LoggingService loggingService;

    @Autowired
    protected Scheduler scheduler;

    @BeforeEach
    void setUpBaseIntegrationTest() throws SchedulerException {

        Mockito.reset(loggingService);
        scheduler.clear();
    }
}
