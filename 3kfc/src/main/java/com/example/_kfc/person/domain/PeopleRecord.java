package com.example._kfc.person.domain;

import java.util.*;

public class PeopleRecord {
    private final Map<Long, Person> record = new HashMap<>();

    /**
     * Gets the person with the id or create a new entity with just an id
     * @param id for the lookup
     * @return person matching the id
     */
    public Person GetOrCreate(long id) {
        return record.computeIfAbsent(id, Person::new);
    }

    public Person GetPerson(long personId) {
        return record.get(personId);
    }

    public Set<Person> GetPersons() {
        return new HashSet<>(record.values());
    }
}
