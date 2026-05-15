package com.example._kfc.person.services;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateTimeService {
    public LocalDate Now() {
        return java.time.LocalDate.now();
    }
}
