package com.shootr.web.batch.job.test;

import com.shootr.web.core.util.ThreadUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class TestJobConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(TestJobConfiguration.class);

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory stepBuilder;

    @Bean(name = "testJob")
    public Job testJob(Step testStep) {
        return jobs.get("testJob").start(testStep).build();
    }

    @Bean
    public Step testStep() {
        return stepBuilder.get("testStep").tasklet((contribution, chunkContext) -> {
            logger.debug("Starting testJob...");

            for (int i = 0; i < 5; i++) {
                ThreadUtil.sleep(1, TimeUnit.SECONDS);
            }
            logger.debug("Completed testJob!!");
            return RepeatStatus.FINISHED;

        }).build();
    }
}
