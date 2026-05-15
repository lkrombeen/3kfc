package com.example._kfc.person.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Person {
    private final long id;
    private String name;
    private LocalDate birthDate;
    private Long partnerId;
    private Set<Long> parentIds = new HashSet<>();
    private Set<Long> childrenIds = new HashSet<>();

    public Person(long id) {
        this.id = id;
    }
}
