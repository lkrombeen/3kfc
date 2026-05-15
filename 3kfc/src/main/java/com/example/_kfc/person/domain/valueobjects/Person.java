package com.example._kfc.person.domain.valueobjects;

import java.util.Set;

public record Person(int id,
                     String name,
                     String birthDate,
                     int parent1Id,
                     int parent2Id,
                     Integer partnerId,
                     Set<Integer> childrenIds) {
}
