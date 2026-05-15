package com.example._kfc.person;

import com.example._kfc.person.domain.valueobjects.Person;
import com.example._kfc.person.entities.PeopleRecord;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonValidatorService {
    private final DateTimeService dateTimeService;

    public PersonValidatorService(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    /**
     * Simple O(n) method to get all the people that fulfill the 3KFC check.
     * @return Set of ids from people that fulfill the check
     */
    public Set<Person> GetValidPeople(PeopleRecord record) {
        return record.GetPersons().stream()
                .filter(this::hasPartner)
                .collect(Collectors.toSet());
    }

    /**
     * Verify if a person has a partner
     * @param person person to check
     * @return true of the person has a partner else false
     */
    private boolean hasPartner(Person person) {
        return person.partnerId() != null;
    }
}
