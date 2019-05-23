package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class HiController {
    @Value("${name}")
    private String personName;

    @RequestMapping("/greeting")
    public String greet(){
        return "hello " + personName;
    }
}