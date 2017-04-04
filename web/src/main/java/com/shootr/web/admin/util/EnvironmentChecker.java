package com.shootr.web.admin.util;


import com.shootr.web.core.config.Profiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.List;


public class EnvironmentChecker {
    private final static Logger logger = LoggerFactory.getLogger(EnvironmentChecker.class);
    private static final List<String> validEnvironments = Profiles.ALL;

    private EnvironmentChecker() {
    }

    /**
     * Set a default profile if it has not been set
     */
    public static void addDefaultProfile(SpringApplication app, String[] args) {
        if (!checkEnvironmentFromArgs(args) && !checkEnvironmentFromSystem()) {
            logger.info("No environment found, setting 'dev' as default");
            app.setAdditionalProfiles("dev");
        }
    }

    private static boolean checkEnvironmentFromArgs(String[] args) {
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        return source.containsProperty("spring.profiles.active") && isValidEnvironment(source.getProperty("spring.profiles.active"));
    }

    public static boolean checkEnvironmentFromSystem() {
        return isValidEnvironment(System.getenv("SPRING_PROFILES_ACTIVE"));
    }

    private static boolean isValidEnvironment(String env) {
        return env != null && !env.isEmpty() && validEnvironments.stream().filter(s -> s.equalsIgnoreCase(env)).findFirst().isPresent();
    }

}
