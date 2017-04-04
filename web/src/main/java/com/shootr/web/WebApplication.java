package com.shootr.web;

import com.shootr.web.admin.util.EnvironmentChecker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


@SpringBootApplication
@ComponentScan(basePackageClasses = WebApplication.class)
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class, DataSourceAutoConfiguration.class, ErrorMvcAutoConfiguration.class, MustacheAutoConfiguration.class})
public class WebApplication {

    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    @Inject
    private Environment env;

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(WebApplication.class);
        EnvironmentChecker.addDefaultProfile(app, args);

        Environment env = app.run(args).getEnvironment();

        String port = env.getProperty("server.port");
        logger.info("Access URLs: \nWeb: http://127.0.0.1:{} \nAdmin: http://127.0.0.1:{}/admin", port, port);
    }

    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            logger.warn("No Spring profile configured, running with default configuration");
        } else {
            logger.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        }
    }
}
