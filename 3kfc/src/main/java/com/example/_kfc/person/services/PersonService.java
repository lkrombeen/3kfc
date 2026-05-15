package com.example._kfc.person.services;

import com.example._kfc.person.api.dtos.PersonDto;
import com.example._kfc.person.domain.PeopleRecord;
import com.example._kfc.person.domain.Person;
import com.example._kfc.person.mappers.PersonMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class PersonService {
    private final PersonValidatorService personValidatorService;
    private final PersonMapper personMapper;
    private final PeopleRecord record = new PeopleRecord();

    public PersonService(PersonValidatorService personValidatorService, PersonMapper personMapper) {
        this.personValidatorService = personValidatorService;
        this.personMapper = personMapper;
    }

    /**
     * Add a new person to the registry or update an existing one
     *
     * @param dto person to add or update
     */
    public PersonDto UpsertPerson(PersonDto dto) {
        var person = record.GetOrCreate(dto.id());

        record.RemoveExistingRelationsOf(person);
        setValuesFor(person, dto);
        record.AddRelationsOf(person);

        return personMapper.toDto(person);
    }

    /**
     * Get the list of all people that are valid according to the 3KFC check
     *
     * @return list of people
     */
    public List<PersonDto> GetValidPeople() {
        return personValidatorService.GetValidPeople(record).stream().map(personMapper::toDto).collect(Collectors.toList());
    }

    private void setValuesFor(Person person, PersonDto values) {
        person.setName(values.name());
        person.setPartnerId(values.partner().id());
        person.setBirthDate(java.time.LocalDate.parse(values.birthDate()));
        person.setParentIds(Set.of(values.parent1().id(), values.parent2().id()));
        person.setChildrenIds(values.children().stream().map(PersonDto.RelatedPersonDto::id).collect(Collectors.toSet()));
    }
}
