package com.example._kfc.person;

import com.example._kfc.person.dtos.PersonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PersonValidatorServiceTest {
    @Mock
    private PersonValidatorService personValidatorService;

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
        var service = new PersonService(personValidatorService);

        var actual = service.AddPerson(Han);

        assertFalse(actual);
    }

    @Test
    void newPersonWithPartnerButNoChildrenReturnsFalse() {
        var service = new PersonService(personValidatorService);

        service.AddPerson(Han);
        var actual = service.AddPerson(Leia);

        assertFalse(actual);
    }

    @Test
    void newPersonWithPartnerButNoAdultChildrenReturnsFalse() {
        var service = new PersonService(personValidatorService);

        service.AddPerson(Han);
        service.AddPerson(Leia);
        service.AddPerson(Ben);
        service.AddPerson(Jacen);
        var actual = service.AddPerson(Jaina);

        assertFalse(actual);
    }

    @Test
    void newPersonWithPartnerButWithAdultChildrenReturnsTrue() {
        var service = new PersonService(personValidatorService);

        service.AddPerson(Han);
        service.AddPerson(Leia);
        service.AddPerson(Ben);
        service.AddPerson(Jacen);
        var actual = service.AddPerson(Jaina);

        assertTrue(actual);
    }

    @Test
    void orderOfInsertionShouldNotMatterAndStillReturnTrue() {
        var service = new PersonService(personValidatorService);

        service.AddPerson(Jaina);
        service.AddPerson(Leia);
        service.AddPerson(Ben);
        service.AddPerson(Jacen);
        var actual = service.AddPerson(Han);

        assertTrue(actual);
    }
}