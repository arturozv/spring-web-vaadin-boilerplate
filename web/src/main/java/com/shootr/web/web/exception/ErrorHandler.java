package com.shootr.web.web.exception;

import com.google.common.base.Throwables;

import com.shootr.web.core.config.Profiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @Value("${spring.profiles.active}")
    private String env;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ModelAndView exception(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        log(exception, request);

        ModelAndView modelAndView = new ModelAndView("error");

        if (!env.equals(Profiles.SPRING_PROFILE_PRODUCTION)) {
            modelAndView.addObject("message", exception.getMessage());
            //modelAndView.addObject("status", response.getStatus());
            modelAndView.addObject("stackTrace", Throwables.getStackTraceAsString(exception));
        }

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class})
    public ModelAndView notFound(NoHandlerFoundException exception, HttpServletRequest request, HttpServletResponse response) {
        log(exception, request);

        ModelAndView modelAndView = new ModelAndView("error");

        if (!env.equals(Profiles.SPRING_PROFILE_PRODUCTION)) {
            modelAndView.addObject("message", exception.getMessage());
            //modelAndView.addObject("status", response.getStatus());
            modelAndView.addObject("path", exception.getRequestURL());
            modelAndView.addObject("method", exception.getHttpMethod());
            modelAndView.addObject("stackTrace", Throwables.getStackTraceAsString(exception));
        }

        return modelAndView;
    }

    void log(Exception e, HttpServletRequest request) {
        logger.error("Error on {} {}: {}", request.getMethod(), request.getRequestURL(), e.getMessage(), e);
    }
}
