package com.example._kfc.person;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/people")
public class PersonController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}