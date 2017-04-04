package com.shootr.web.batch.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResult {

    private String name;
    private String jobId;
    private String status;
    private Long duration;
    private String exitDescription;

}
