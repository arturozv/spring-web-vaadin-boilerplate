package com.shootr.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.mustache.jmustache.LocalizationMessageInterceptor;

import java.util.Locale;

import javax.inject.Inject;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Inject
    private MessageSource messageSource;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(getLocalizationMessageInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Bean
    public LocalizationMessageInterceptor getLocalizationMessageInterceptor() {
        LocalizationMessageInterceptor localizationMessageInterceptor = new LocalizationMessageInterceptor();
        localizationMessageInterceptor.setMessageSource(messageSource);
        localizationMessageInterceptor.setLocaleResolver(localeResolver());
        return localizationMessageInterceptor;

    }
}
