package com.example._kfc.person.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PeopleRecord {
    private final Map<Long, Person> record = new HashMap<>();

    /**
     * Gets the person with the id or create a new entity with just an id
     *
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

    /**
     * Remove relations of existing person
     *
     * @param person the person to remove all relations from and to
     */
    public void RemoveExistingRelationsOf(Person person) {
        // Remove id from possible partner
        if (person.getPartnerId() != null) {
            var partner = GetPerson(person.getPartnerId());
            if (partner != null) partner.setPartnerId(null);
        }

        // Remove id from possible parents
        for (var parentId : person.getParentIds()) {
            var parent = GetPerson(parentId);
            if (parent != null) parent.getChildrenIds().remove(person.getId());
        }

        // Remove id from possible children
        for (var childId : person.getChildrenIds()) {
            var child = GetPerson(childId);
            if (child != null) child.getParentIds().remove(person.getId());
        }
    }

    /**
     * Introduces relations from the other person to the given person
     *
     * @param person the given person
     */
    public void AddRelationsOf(Person person) {
        // TODO add partner relation
        // TODO add child relations
        // TODO add parent relations
    }
}
