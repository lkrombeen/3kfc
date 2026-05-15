package com.example._kfc.person;

import org.springframework.stereotype.Component;

@Component
public class DateTimeService {
    public String Now() {
        return java.time.LocalDate.now().toString();
    }
}
