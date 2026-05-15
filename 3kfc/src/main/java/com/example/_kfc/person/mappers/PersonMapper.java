package com.example._kfc.person.mappers;

import com.example._kfc.person.api.dtos.PersonDto;
import com.example._kfc.person.domain.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public final class PersonMapper {
    public PersonDto toDto(Person person) {
        var parents = new ArrayList<>(person.getParentIds());
        return new PersonDto(
                person.getId(),
                person.getName(),
                person.getBirthDate() == null ? null : person.getBirthDate().toString(),
                parents.size() > 0 ? new PersonDto.RelatedPersonDto(parents.getFirst()) : null,
                parents.size() > 1 ? new PersonDto.RelatedPersonDto(parents.get(1)) : null,
                person.getPartnerId() == null ? null : new PersonDto.RelatedPersonDto(person.getPartnerId()),
                person.getChildrenIds().stream().map(PersonDto.RelatedPersonDto::new).collect(Collectors.toList())
        );
    }
}
