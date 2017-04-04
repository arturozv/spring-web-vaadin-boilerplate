package com.shootr.web.batch.core;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class CustomJobLauncher {
    private static final Logger logger = LoggerFactory.getLogger(CustomJobLauncher.class);

    private Cache<String, Future<JobResult>> asyncJobs = CacheBuilder.newBuilder().concurrencyLevel(4).expireAfterWrite(7, TimeUnit.DAYS).build();

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ScheduledJobProperties scheduledJobProperties;

    public JobResult executeJob(Job job) {
        String jobId = JobIdFactory.newJobId(job.getName());
        return executeJob(jobId, job);
    }

    @Async
    public Future<JobResult> executeJobAsync(String jobId, Job job) {
        return new AsyncResult<>(this.executeJob(jobId, job));
    }

    public JobResult executeJob(String jobId, Job job) {
        JobResult jobResult;
        JobExecution execution = null;
        try {
            if (jobsEnabled()) {
                logger.debug("Launching job " + job.getName() + ". Id: " + jobId);
                execution = jobLauncher.run(job, new JobParametersBuilder().addString("run.id", jobId).toJobParameters());
            }
        } catch (Exception e) {
            logger.error("Error while executing job " + job.getName(), e);
        } finally {
            jobResult = getJobResult(job, execution, jobId);
        }

        if (jobsEnabled()) {
            logger.debug("Job finished. Result: " + jobResult);
        }
        return jobResult;
    }

    private JobResult getJobResult(Job job, JobExecution execution, String jobId) {
        JobResult result = new JobResult();
        result.setName(job.getName());
        result.setJobId(jobId);
        if (jobsEnabled()) {
            if (execution != null) {
                if (execution.getStatus() != null) {
                    result.setStatus(execution.getStatus().toString());
                }
                if (execution.getStartTime() != null && execution.getEndTime() != null) {
                    result.setDuration(execution.getEndTime().getTime() - execution.getStartTime().getTime());
                }
                if (execution.getExitStatus() != null) {
                    result.setExitDescription(execution.getExitStatus().getExitDescription());
                }
            }
        } else {
            result.setStatus("BATCH_DISABLED");
            result.setDuration(0l);
        }
        return result;
    }

    public Cache<String, Future<JobResult>> getAsyncJobs() {
        return asyncJobs;
    }

    private boolean jobsEnabled() {
        return scheduledJobProperties.isEnabled();
    }
}
