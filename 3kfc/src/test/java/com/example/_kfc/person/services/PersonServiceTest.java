package com.example._kfc.person.services;

import com.example._kfc.person.api.dtos.PersonDto;
import com.example._kfc.person.domain.Person;
import com.example._kfc.person.mappers.PersonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonValidatorService validator;

    @Mock
    PersonMapper mapper;

    @Test
    void upsertPersonReturnsMappedDto() {
        var service = new PersonService(validator, mapper);
        var input = new PersonDto(
                10,
                "Luke",
                "1977-05-25",
                new PersonDto.RelatedPersonDto(1),
                new PersonDto.RelatedPersonDto(2),
                new PersonDto.RelatedPersonDto(20),
                List.of(new PersonDto.RelatedPersonDto(100L))
        );
        var expectedDto = new PersonDto(
                10,
                "Luke",
                "1977-05-25",
                new PersonDto.RelatedPersonDto(1),
                new PersonDto.RelatedPersonDto(2),
                new PersonDto.RelatedPersonDto(20),
                List.of(new PersonDto.RelatedPersonDto(100L))
        );
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        when(mapper.toDto(any())).thenReturn(expectedDto);

        var result = service.UpsertPerson(input);

        assertEquals(expectedDto, result);
        verify(mapper).toDto(personCaptor.capture());
        var p = personCaptor.getValue();
        assertEquals(10, p.getId());
        assertEquals("Luke", p.getName());
        assertEquals(LocalDate.of(1977, 5, 25), p.getBirthDate());
        assertEquals(Set.of(1L, 2L), p.getParentIds());
        assertEquals(20L, p.getPartnerId());
        assertEquals(Set.of(100L), p.getChildrenIds());
    }

    @Test
    void getValidPeopleReturnsMappedList() {
        var service = new PersonService(validator, mapper);

        var person = new Person(
                10,
                "Luke",
                LocalDate.of(1977, 5, 25),
                20L,
                Set.of(1L, 2L),
                Set.of()
        );

        var dto = new PersonDto(
                10,
                "Luke",
                "1977-05-25",
                new PersonDto.RelatedPersonDto(1),
                new PersonDto.RelatedPersonDto(2),
                new PersonDto.RelatedPersonDto(20),
                List.of()
        );

        when(validator.GetValidPeople(any())).thenReturn(Set.of(person));
        when(mapper.toDto(person)).thenReturn(dto);

        var result = service.GetValidPeople();

        assertEquals(1, result.size());
        assertEquals(dto, result.getFirst());

        verify(validator).GetValidPeople(any());
        verify(mapper).toDto(person);
    }
}
