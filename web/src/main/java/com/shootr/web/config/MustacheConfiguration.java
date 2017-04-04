package com.shootr.web.config;

import com.samskivert.mustache.Mustache;

import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties({MustacheProperties.class})
public class MustacheConfiguration extends MustacheAutoConfiguration {

    public MustacheConfiguration(MustacheProperties mustache, Environment environment, ApplicationContext applicationContext) {
        super(mustache, environment, applicationContext);
    }

    @Override
    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader mustacheTemplateLoader) {
        return super.mustacheCompiler(mustacheTemplateLoader).defaultValue("");
    }
}


