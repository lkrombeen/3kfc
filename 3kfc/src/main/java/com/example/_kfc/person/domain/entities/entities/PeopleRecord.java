package com.example._kfc.person.domain.entities.entities;

import com.example._kfc.person.domain.valueobjects.Person;

import java.util.*;

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

    public Set<Person> GetPersons() {
        return new HashSet<>(record.values());
    }
}
