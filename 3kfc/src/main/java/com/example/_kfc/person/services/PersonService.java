package com.example._kfc.person.services;

import com.example._kfc.person.api.dtos.PersonDto;
import com.example._kfc.person.domain.PeopleRecord;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonValidatorService personValidatorService;
    private final PeopleRecord record = new PeopleRecord();

    public PersonService(PersonValidatorService personValidatorService) {
        this.personValidatorService = personValidatorService;
    }

    /**
     * Add a new person to the registry or update an existing one
     *
     * @param dto person to add or update
     * @return true if the 3kfc check is passing with the current people, else false
     */
    public boolean UpsertPerson(PersonDto dto) {
        var person = record.GetOrCreate(dto.id());

        // TODO remove existing relations

        person.setName(dto.name());
        person.setPartnerId(dto.partner().id());
        person.setBirthDate(java.time.LocalDate.parse(dto.birthDate()));
        person.setParentIds(Set.of(dto.parent1().id(), dto.parent2().id()));
        person.setChildrenIds(dto.children().stream().map(PersonDto.RelatedPersonDto::id).collect(Collectors.toSet()));

        // TODO map to response DTO instead of boolean
        return !personValidatorService.GetValidPeople(record).isEmpty();
    }

}
