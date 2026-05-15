package com.example._kfc.person.entities;

import com.example._kfc.person.domain.valueobjects.Person;
import com.example._kfc.person.dtos.PersonDto;
import com.example._kfc.person.mappers.PersonMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PeopleRecord {
    private final Map<Integer, Person> record = new HashMap<>();
    private final Set<Integer> validPeople = new HashSet<>();

    /**
     * Add a person to the registry
     * @param dto person to add or update
     * @return true if the 3kfc check has passed, otherwise false
     */
    public boolean AddPerson(PersonDto dto) {
        var person = PersonMapper.FromDto(dto);
        this.record.put(person.id(), person);
        return false;
    }
}
