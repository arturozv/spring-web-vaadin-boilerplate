package com.shootr.web.core.service

import com.google.common.collect.ImmutableMap
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import spock.lang.Specification

class MessageByLocaleServiceTest extends Specification {

    def MessageByLocaleService messageByLocaleService

    def setup() {
        messageByLocaleService = new MessageByLocaleService();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:locale/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        messageByLocaleService.setMessageSource(messageSource);
    }

    def "localized messages"() {

        expect: "translated message"
        result == messageByLocaleService.getMessage("test.simple", locale);

        where: "validate message for every language"
        locale | result
        Locale.ENGLISH | "name"
        new Locale("es") | "nombre"
    }

    def "localized messages with parameters"() {

        expect: "translated message with substitutions"
        def map = ImmutableMap.of("name", "nameValue")
        result == messageByLocaleService.getMessage("test.withparams", locale, map);

        where: "validate message for every language"
        locale | result
        Locale.ENGLISH | "value after substitution: nameValue"
        new Locale("es") | "valor después de substituir: nameValue"
    }

}
