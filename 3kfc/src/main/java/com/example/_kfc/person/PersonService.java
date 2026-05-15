package com.example._kfc.person;

import com.example._kfc.person.dtos.PersonDto;
import com.example._kfc.person.entities.PeopleRecord;
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
     * @param dto person to add or update
     * @return true if the 3kfc check is passing with the current people, else false
     */
    public boolean AddPerson(PersonDto dto) {
        var person = PersonMapper.FromDto(dto);
        record.AddPerson(person);

        personValidatorService.validatePerson(person, record);

        return personValidatorService.passes3KFCCheck();
    }
}
