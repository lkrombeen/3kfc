package com.example._kfc.person.api.dtos;

import java.util.List;

public record PersonDto(
        long id,
        String name,
        String birthDate,
        RelatedPersonDto parent1,
        RelatedPersonDto parent2,
        RelatedPersonDto partner,
        List<RelatedPersonDto> children
) {
    public record RelatedPersonDto(long id) {
    }
}