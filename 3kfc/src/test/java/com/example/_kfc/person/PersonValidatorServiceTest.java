package com.example._kfc.person;

import com.example._kfc.person.domain.valueobjects.Person;
import com.example._kfc.person.entities.PeopleRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonValidatorServiceTest {
    @Mock
    private DateTimeService dateTimeService;

    private final Person Han = new Person(5, "Han Solo", "1942-07-13",
            1,
            2,
            6,
            Set.of(7, 8, 9));
    private final Person Leia = new Person(6, "Leia Organa", "1942-07-13",
            3,
            4,
            6,
            Set.of(7, 8, 9));
    private final Person Ben = new Person(7, "Ben Solo", "1966-01-21",
            5,
            6,
            null,
            new HashSet<Integer>());
    private final Person Jacen = new Person(8, "Jacen Solo", "1967-01-21",
            5,
            6,
            null,
            new HashSet<Integer>());
    private final Person Jaina = new Person(9, "Jaina Solo", "1968-01-21",
            5,
            6,
            null,
            new HashSet<Integer>());

    @Test
    void personWithoutPartnerReturnsFalse() {
        var record = new PeopleRecord();
        record.AddPerson(Jaina);
        var service = new PersonValidatorService(dateTimeService);

        var actual = service.GetValidPeople(record);

        assertEquals(0, actual.size());
    }
}