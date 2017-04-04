package com.shootr.web.batch;

import com.shootr.web.batch.core.CustomJobLauncher;
import com.shootr.web.core.config.Profiles;

import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledJobRunner {

    @Autowired
    private CustomJobLauncher jobLauncher;

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    @Qualifier("testJob")
    private Job testJob;

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void testJob() {
        executeIfNotDev(testJob);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void testJobCron() {
        executeIfNotDev(testJob);
    }

    void executeIfNotDev(Job job) {
        if (!isDevelopment()) {
            jobLauncher.executeJob(job);
        }
    }

    private boolean isDevelopment() {
        return Profiles.SPRING_PROFILE_DEVELOPMENT.equals(env);
    }
}
