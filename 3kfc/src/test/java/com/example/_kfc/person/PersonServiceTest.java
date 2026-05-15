package com.example._kfc.person;

import com.example._kfc.person.domain.Person;
import com.example._kfc.person.api.dtos.PersonDto;
import com.example._kfc.person.services.PersonService;
import com.example._kfc.person.services.PersonValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonValidatorService personValidatorService;

    private final PersonDto Han = new PersonDto(5, "Han Solo", "1942-07-13",
            new PersonDto.RelatedPersonDto(1),
            new PersonDto.RelatedPersonDto(2),
            new PersonDto.RelatedPersonDto(6),
            new ArrayList<>());

    @Test
    void newPersonReturnsFalse() {
        var service = new PersonService(personValidatorService);

        when(personValidatorService.GetValidPeople(Mockito.any())).thenReturn(new HashSet<>());

        var actual = service.UpsertPerson(Han);

        assertFalse(actual);
    }

    @Test
    void newPersonReturnsTrue() {
        var service = new PersonService(personValidatorService);

        when(personValidatorService.GetValidPeople(Mockito.any())).thenReturn(Set.of(
                new Person(1L, "name", LocalDate.now(), 4L, Set.of(2L, 3L), Set.of())
        ));

        var actual = service.UpsertPerson(Han);

        assertTrue(actual);
    }
}
