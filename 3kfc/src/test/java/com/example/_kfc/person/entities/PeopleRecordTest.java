package com.example._kfc.person.entities;

import com.example._kfc.person.dtos.PersonDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeopleRecordTest {
    private final PersonDto Han = new PersonDto(5, "Han Solo", "1942-07-13",
            new PersonDto.RelatedPersonDto(1),
            new PersonDto.RelatedPersonDto(2),
            new PersonDto.RelatedPersonDto(6),
            new ArrayList<>());
    private final PersonDto Leia = new PersonDto(6, "Leia Organa", "1942-07-13",
            new PersonDto.RelatedPersonDto(3),
            new PersonDto.RelatedPersonDto(4),
            new PersonDto.RelatedPersonDto(5),
            new ArrayList<>());
    private final PersonDto Ben = new PersonDto(5, "Ben Solo", "1966-01-21",
            new PersonDto.RelatedPersonDto(5),
            new PersonDto.RelatedPersonDto(6),
            new PersonDto.RelatedPersonDto(7),
            new ArrayList<>());
    private final PersonDto Jacen = new PersonDto(5, "Jacen Solo", "1967-01-21",
            new PersonDto.RelatedPersonDto(5),
            new PersonDto.RelatedPersonDto(6),
            new PersonDto.RelatedPersonDto(7),
            new ArrayList<>());
    private final PersonDto Jaina = new PersonDto(5, "Jaina Solo", "1968-01-21",
            new PersonDto.RelatedPersonDto(5),
            new PersonDto.RelatedPersonDto(6),
            new PersonDto.RelatedPersonDto(7),
            new ArrayList<>());

    @Test
    void newPersonReturnsFalse() {
        var record = new PeopleRecord();

        var actual = record.AddPerson(Han);

        assertFalse(actual);
    }

    @Test
    void newPersonWithPartnerButNoChildrenReturnsFalse() {
        var record = new PeopleRecord();

        record.AddPerson(Han);
        var actual = record.AddPerson(Leia);

        assertFalse(actual);
    }

    @Test
    void newPersonWithPartnerButNoAdultChildrenReturnsFalse() {
        var record = new PeopleRecord();

        record.AddPerson(Han);
        record.AddPerson(Leia);
        record.AddPerson(Ben);
        record.AddPerson(Jacen);
        var actual = record.AddPerson(Jaina);

        assertFalse(actual);
    }

    @Test
    void newPersonWithPartnerButWithAdultChildrenReturnsTrue() {
        var record = new PeopleRecord();

        record.AddPerson(Han);
        record.AddPerson(Leia);
        record.AddPerson(Ben);
        record.AddPerson(Jacen);
        var actual = record.AddPerson(Jaina);

        assertTrue(actual);
    }
}