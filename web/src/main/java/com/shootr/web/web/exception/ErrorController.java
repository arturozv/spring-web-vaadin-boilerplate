package com.shootr.web.web.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorController {

    @RequestMapping("/**")
    public String handlerNotMappingRequest(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders httpHeaders) throws NoHandlerFoundException {
        throw new NoHandlerFoundException(request.getMethod(), request.getRequestURL().toString(), httpHeaders);
    }

    @RequestMapping("/testException")
    public String testException() {
        throw new RuntimeException("testException");
    }
}
