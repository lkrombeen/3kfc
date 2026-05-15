package com.example._kfc.person;

import com.example._kfc.person.domain.valueobjects.Person;
import com.example._kfc.person.entities.PeopleRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonValidatorServiceTest {
    @Mock
    private DateTimeService dateTimeService;

    // Values for a happy little family that meets the 3KFC checks
    private final Person parent1 = new Person(5, "Han Solo", "1942-07-13",
            1,
            2,
            6,
            Set.of(7, 8, 9));
    private final Person parent2 = new Person(6, "Leia Organa", "1942-07-13",
            3,
            4,
            6,
            Set.of(7, 8, 9));
    private final Person child1 = new Person(7, "Ben Solo", "1966-01-21",
            5,
            6,
            null,
            new HashSet<>());
    private final Person child2 = new Person(8, "Jacen Solo", "1967-01-21",
            5,
            6,
            null,
            new HashSet<>());
    private final Person child3 = new Person(9, "Jaina Solo", "2012-01-21",
            5,
            6,
            null,
            new HashSet<>());

    @Test
    void personWithoutPartnerReturnsEmptyList() {
        var record = new PeopleRecord();
        record.AddPerson(child3);
        var service = new PersonValidatorService(dateTimeService);

        var actual = service.GetValidPeople(record);

        assertEquals(0, actual.size());
    }

    @Test
    void personWithoutThreeChildrenReturnsEmptyList() {
        var record = new PeopleRecord();
        record.AddPerson(child1);
        record.AddPerson(child2);
        record.AddPerson(child3);
        // Override with 2 children
        record.AddPerson(new Person(parent2.id(), parent2.name(), parent2.birthDate(), 1, 2, parent1.id(), Set.of(child1.id(), child2.id())));
        record.AddPerson(new Person(parent1.id(), parent1.name(), parent1.birthDate(), 1, 2, parent2.id(), Set.of(child1.id(), child2.id())));
        var service = new PersonValidatorService(dateTimeService);

        var actual = service.GetValidPeople(record);

        assertEquals(0, actual.size());
    }

    @Test
    void personWithTheChildrenButNoMinorsReturnsEmptyList() {
        var record = new PeopleRecord();
        record.AddPerson(child1);
        record.AddPerson(child2);
        record.AddPerson(child3);
        record.AddPerson(new Person(parent2.id(), parent2.name(), parent2.birthDate(), 1, 2, parent1.id(), Set.of(child1.id(), child2.id(), child3.id())));
        record.AddPerson(new Person(parent1.id(), "Han Solo", "2024-01-02", 1, 2, parent2.id(), Set.of(child1.id(), child2.id(), child3.id())));
        var service = new PersonValidatorService(dateTimeService);

        when(dateTimeService.Now()).thenReturn(LocalDate.of(2030, 1, 21));

        var actual = service.GetValidPeople(record);

        assertEquals(0, actual.size());
    }

    @Test
    void personWithThreeChildrenReturnsValidParents() {
        var record = new PeopleRecord();
        record.AddPerson(child1);
        record.AddPerson(child2);
        record.AddPerson(child3);
        record.AddPerson(new Person(parent2.id(), parent2.name(), parent2.birthDate(), 1, 2, parent1.id(), Set.of(child1.id(), child2.id(), child3.id())));
        record.AddPerson(new Person(parent1.id(), "Han Solo", "2024-01-02", 1, 2, parent2.id(), Set.of(child1.id(), child2.id(), child3.id())));
        var service = new PersonValidatorService(dateTimeService);

        when(dateTimeService.Now()).thenReturn(LocalDate.of(2030, 1, 1));

        var actual = service.GetValidPeople(record);

        assertEquals(2, actual.size());
    }
}