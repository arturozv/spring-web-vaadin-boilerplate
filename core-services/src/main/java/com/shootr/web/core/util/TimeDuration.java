package com.shootr.web.core.util;

import java.time.Duration;
import java.time.Instant;

public class TimeDuration {

    private TimeDuration() {
    }

    public static long getMillisSince(Instant start) {
        return Duration.between(start, Instant.now()).toMillis();
    }
}
