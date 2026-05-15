package com.example._kfc.person.entities;

import com.example._kfc.person.domain.valueobjects.Person;

import java.util.HashMap;
import java.util.Map;

public class PeopleRecord {
    private final Map<Integer, Person> record = new HashMap<>();

    /**
     * Add a person to the registry
     *
     * @param person person to add or update
     */
    public void AddPerson(Person person) {
        record.put(person.id(), person);
    }

    public Person GetPerson(int personId) {
        return record.get(personId);
    }
}
