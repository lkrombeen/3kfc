package com.example._kfc.person;

import com.example._kfc.person.dtos.PersonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        when(personValidatorService.passes3KFCCheck()).thenReturn(false);

        var actual = service.AddPerson(Han);

        assertFalse(actual);
    }

    @Test
    void newPersonReturnsTrue() {
        var service = new PersonService(personValidatorService);

        when(personValidatorService.passes3KFCCheck()).thenReturn(true);

        var actual = service.AddPerson(Han);

        assertTrue(actual);
    }
}
