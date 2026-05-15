package com.example._kfc.person.services;

import com.example._kfc.person.api.dtos.PersonDto;
import com.example._kfc.person.domain.entities.entities.PeopleRecord;
import com.example._kfc.person.mappers.PersonMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonValidatorService personValidatorService;
    private final PeopleRecord record = new PeopleRecord();

    public PersonService(PersonValidatorService personValidatorService) {
        this.personValidatorService = personValidatorService;
    }

    /**
     * Add a new person to the registry
     *
     * @param dto person to add or update
     * @return true if the 3kfc check is passing with the current people, else false
     */
    public boolean AddPerson(PersonDto dto) {
        var person = PersonMapper.FromDto(dto);

        var oldEntry = record.GetPerson(person.id());
        record.AddPerson(person);
        // TODO add / update  family members

        // TODO map to response DTO instead of boolean
        return !personValidatorService.GetValidPeople(record).isEmpty();
    }
}
