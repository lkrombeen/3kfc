package com.example._kfc.person.entities;

import com.example._kfc.person.domain.valueobjects.Person;
import com.example._kfc.person.dtos.PersonDto;

import java.util.HashMap;
import java.util.Map;

public class PeopleRecord {
    private final Map<Integer, Person> record = new HashMap<>();

    /**
     * Add a person to the registry
     * @param dto person to add or update
     * @return true if the 3kfc check has passed, otherwise false
     */
    public boolean AddPerson(PersonDto dto) {
        return false;
    }
}
