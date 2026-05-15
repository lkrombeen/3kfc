package com.example._kfc.person.dtos;

import java.util.List;

public record PersonDto(
        int id,
        String name,
        String birthDate,
        RelatedPersonDto parent1,
        RelatedPersonDto parent2,
        RelatedPersonDto partner,
        List<RelatedPersonDto> children
) {
    public record RelatedPersonDto(int id) {
    }
}