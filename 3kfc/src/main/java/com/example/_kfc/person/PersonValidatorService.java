package com.example._kfc.person;

import com.example._kfc.person.domain.valueobjects.Person;
import com.example._kfc.person.entities.PeopleRecord;
import org.springframework.stereotype.Service;

@Service
public class PersonValidatorService {
    private final DateTimeService dateTimeService;

    public PersonValidatorService(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    public boolean passes3KFCCheck() {
        return false;
    }

    public boolean validatePerson(Person person, PeopleRecord record) {
        return hasThreeChildren(person, record);
    }

    /**
     * Validate if the person has 3 children, and they have the same parent as the partner of the person
     * @param person person to validate
     * @return true if checks are met, else false
     */
    private boolean hasThreeChildren(Person person, PeopleRecord record) {
        if (person.childrenIds().size() != 3) return false;

        var partnerId = person.partnerId();
        return person.childrenIds().stream().allMatch(id -> hasParent(id, partnerId, record));
    }

    /**
     * Check if a person has a given parent
     * @param personId id of the person to check
     * @param parentId if of the required parent
     * @return true if one of the parents of the person is equal to parentId, else false
     */
    private boolean hasParent(int personId, int parentId, PeopleRecord record) {
        var person = record.GetPerson(personId);
        if (person == null) return false;

        return person.parent1Id() == parentId || person.parent2Id() == parentId;
    }
}
