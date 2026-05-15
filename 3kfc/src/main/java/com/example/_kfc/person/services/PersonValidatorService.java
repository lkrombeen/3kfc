package com.example._kfc.person.services;

import com.example._kfc.person.domain.valueobjects.Person;
import com.example._kfc.person.domain.entities.entities.PeopleRecord;
import org.springframework.stereotype.Service;

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
     *
     * @return Set of ids from people that fulfill the check
     */
    public Set<Person> GetValidPeople(PeopleRecord record) {
        return record.GetPersons().stream()
                .filter(this::hasPartner)
                .filter(person -> hasThreeChildrenWithCorrectParents(person, record))
                .filter(person -> hasNotOnlyMinorChildren(person, record))
                .collect(Collectors.toSet());
    }

    /**
     * Verify if a person has a partner
     *
     * @param person person to check
     * @return true of the person has a partner else false
     */
    private boolean hasPartner(Person person) {
        return person.partnerId() != null;
    }

    /**
     * Checks if a person has exactly 3 children and all 3 have the same partner listed as mother or faster
     *
     * @param person to check
     * @param record of people
     * @return true if the check matches else false
     */
    private boolean hasThreeChildrenWithCorrectParents(Person person, PeopleRecord record) {
        if (person.childrenIds().size() != 3) return false;

        var partnerId = person.partnerId();
        return person.childrenIds().stream().map(record::GetPerson).allMatch(c -> c.parent1Id() == partnerId || c.parent2Id() == partnerId);
    }

    /**
     * Checks if a person has at least one child under 18
     * @param person person to check
     * @param record of people
     * @return true if the person has at least 1 minor child
     */
    private boolean hasNotOnlyMinorChildren(Person person, PeopleRecord record) {
        return person.childrenIds().stream().map(record::GetPerson)
                .anyMatch(this::isUnder18);
    }

    /**
     * Check if a person is under 18.
     * @param person person to check
     * @return true if the person is 17 or younger, else false
     */
    public boolean isUnder18(Person person) {
        var date = java.time.LocalDate.parse(person.birthDate());
        var eighteenYearsAgo = dateTimeService.Now().minusYears(18);
        return date.isAfter(eighteenYearsAgo);
    }
}
