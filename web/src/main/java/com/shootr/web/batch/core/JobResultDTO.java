package com.shootr.web.batch.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResultDTO {
    private String jobId;
    private String status;
    private String statusDetail;
    private JobResult jobResult;
}
