package com.shootr.web.core.config;

import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

public final class Profiles {

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_CI = "ci";
    public static final String SPRING_PROFILE_TEST = "tst";
    public static final String SPRING_PROFILE_PRODUCTION = "pro";
    public static List<String> ALL = Arrays.asList(SPRING_PROFILE_DEVELOPMENT, SPRING_PROFILE_CI, SPRING_PROFILE_TEST, SPRING_PROFILE_PRODUCTION);

    private Profiles() {
    }

    public static boolean isDev(Environment environment) {
        return Arrays.asList(environment.getActiveProfiles()).contains(SPRING_PROFILE_DEVELOPMENT);
    }
}
