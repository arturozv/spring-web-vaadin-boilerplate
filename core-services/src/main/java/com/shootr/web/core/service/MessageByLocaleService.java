package com.shootr.web.core.service;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
public class MessageByLocaleService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String id, Locale locale) {
        return messageSource.getMessage(id, null, locale);
    }

    public String getMessage(String id, Locale locale, Map<String, Object> replaceParams) {
        String localizedComment = getMessage(id, locale);

        if (localizedComment == null) {
            return null;
        }

        if (replaceParams != null) {
            localizedComment = new StrSubstitutor(replaceParams, "{", "}").replace(localizedComment);
        }
        return localizedComment;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
