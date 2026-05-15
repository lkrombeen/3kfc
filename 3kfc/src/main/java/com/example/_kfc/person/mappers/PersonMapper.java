package com.example._kfc.person.mappers;

import com.example._kfc.person.domain.valueobjects.Person;
import com.example._kfc.person.api.dtos.PersonDto;

import java.util.stream.Collectors;

public final class PersonMapper {
    public static Person FromDto(PersonDto dto) {
        return new Person(dto.id(), dto.name(), dto.birthDate(),
                dto.parent1().id(),
                dto.parent2().id(),
                dto.partner().id(),
                dto.children().stream().map(PersonDto.RelatedPersonDto::id).collect(Collectors.toSet()));
    }
}
