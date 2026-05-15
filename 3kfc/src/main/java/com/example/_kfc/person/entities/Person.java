package com.example._kfc.person.entities;

import java.util.Set;

public record Person(int Id,
                     String name,
                     String birthDate,
                     Person parent1,
                     Person parent2,
                     Person partner,
                     Set<Person> children) {
}
