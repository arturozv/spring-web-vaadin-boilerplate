package com.shootr.web.web;

import com.shootr.web.core.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    String getRoot(Map<String, Object> model) {

        model.put("time", new Date());
        model.put("entities", testService.findAll());

        return "index";
    }

}
