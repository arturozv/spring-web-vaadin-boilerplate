package com.shootr.web.batch.core;

import org.springframework.batch.core.scope.context.ChunkContext;

import java.util.UUID;

public interface JobIdFactory {

    static String newJobId(String jobName) {
        return jobName + "-" + UUID.randomUUID();
    }

    static String getJobId(ChunkContext chunkContext) {
        String jobId = null;
        try {
            jobId = (String) chunkContext.getStepContext().getJobParameters().get("run.id");
        } catch (Exception e) {
        }
        return jobId;
    }
}
