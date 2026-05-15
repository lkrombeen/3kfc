package com.example._kfc.person.services;

import com.example._kfc.person.domain.PeopleRecord;
import com.example._kfc.person.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonValidatorServiceTest {
    @Mock
    private DateTimeService dateTimeService;

    // Values for a happy little family that meets the 3KFC checks
    private final Person parent1 = new Person(5, "Han Solo", LocalDate.parse("1942-07-13"),
            6L,
            Set.of(1L, 2L),
            Set.of(7L, 8L, 9L));
    private final Person parent2 = new Person(6, "Leia Organa", LocalDate.parse("1942-07-13"),
            6L,
            Set.of(3L, 4L),
            Set.of(7L, 8L, 9L));
    private final Person child1 = new Person(7, "Ben Solo", LocalDate.parse("1966-01-21"),
            null,
            Set.of(5L, 6L),
            new HashSet<>());
    private final Person child2 = new Person(8, "Jacen Solo", LocalDate.parse("1967-01-21"),
            null,
            Set.of(5L, 6L),
            new HashSet<>());
    private final Person child3 = new Person(9, "Jaina Solo", LocalDate.parse("2012-01-21"),
            null,
            Set.of(5L, 6L),
            new HashSet<>());

    @Test
    void personWithoutPartnerReturnsEmptyList() {
        var record = new PeopleRecord();
        var person = record.GetOrCreate(child3.getId());
        setValuesFor(person, child3);
        var service = new PersonValidatorService(dateTimeService);

        var actual = service.GetValidPeople(record);

        assertEquals(0, actual.size());
    }

    @Test
    void personWithoutThreeChildrenReturnsEmptyList() {
        // Arrange
        var record = setupHappyLittleFamily();
        var parent1Person = record.GetPerson(parent1.getId());
        var parent2Person = record.GetPerson(parent2.getId());
        // Override parents to have only 2 children
        parent1Person.setChildrenIds(parent1Person.getChildrenIds().stream().filter(c -> c != child3.getId()).collect(Collectors.toSet()));
        parent2Person.setChildrenIds(parent2Person.getChildrenIds().stream().filter(c -> c != child3.getId()).collect(Collectors.toSet()));
        var service = new PersonValidatorService(dateTimeService);

        // Act
        var actual = service.GetValidPeople(record);

        // Assert
        assertEquals(0, actual.size());
    }

    @Test
    void personWithTheChildrenButNoMinorsReturnsEmptyList() {
        // Arrange
        var record = setupHappyLittleFamily();
        var service = new PersonValidatorService(dateTimeService);
        // Mock so that all children will be over 18
        when(dateTimeService.Now()).thenReturn(LocalDate.of(2030, 1, 21));

        // Act
        var actual = service.GetValidPeople(record);

        // Assert
        assertEquals(0, actual.size());
    }

    @Test
    void personWithThreeChildrenReturnsValidParents() {
        // Arrange
        var record = setupHappyLittleFamily();
        var service = new PersonValidatorService(dateTimeService);

        // Mock so that at least on child is below 18
        when(dateTimeService.Now()).thenReturn(LocalDate.of(2030, 1, 1));

        // Act
        var actual = service.GetValidPeople(record);

        // Assert
        assertEquals(2, actual.size());
    }

    private PeopleRecord setupHappyLittleFamily() {
        var record = new PeopleRecord();
        var child1Person = record.GetOrCreate(child1.getId());
        setValuesFor(child1Person, child1);
        var child2Person = record.GetOrCreate(child2.getId());
        setValuesFor(child2Person, child2);
        var child3Person = record.GetOrCreate(child3.getId());
        setValuesFor(child3Person, child3);
        var parent1Person = record.GetOrCreate(parent1.getId());
        setValuesFor(parent1Person, parent1);
        var parent2Person = record.GetOrCreate(parent2.getId());
        setValuesFor(parent2Person, parent2);

        return record;
    }

    private void setValuesFor(Person person, Person values) {
        person.setName(values.getName());
        person.setPartnerId(values.getPartnerId());
        person.setBirthDate(values.getBirthDate());
        person.setParentIds(new HashSet<>(values.getParentIds()));
        person.setChildrenIds(new HashSet<>(values.getChildrenIds()));
    }
}