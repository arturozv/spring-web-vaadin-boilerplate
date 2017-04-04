package com.shootr.web.core.util;

import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    private ThreadUtil() {
    }

    public static void sleep(long duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (Exception ignored) {
        }
    }

}
