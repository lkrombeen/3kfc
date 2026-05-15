package com.example._kfc.person.mappers;

import com.example._kfc.person.api.dtos.PersonDto;
import com.example._kfc.person.domain.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {

    @Test
    void toDtoShouldMapAllFieldsCorrectly() {
        Person person = new Person(
                10,
                "Luke Skywalker",
                LocalDate.of(1977, 5, 25),
                20L,
                Set.of(1L, 2L),
                Set.of(100L, 101L)
        );

        PersonDto dto = new PersonMapper().toDto(person);

        assertEquals(10, dto.id());
        assertEquals("Luke Skywalker", dto.name());
        assertEquals("1977-05-25", dto.birthDate());
        assertTrue(dto.parent1().id() == 1 || dto.parent2().id() == 1);
        assertTrue(dto.parent1().id() == 2 || dto.parent2().id() == 2);
        assertEquals(20, dto.partner().id());
        assertEquals(2, dto.children().size());
        assertTrue(dto.children().stream().anyMatch(c -> c.id() == 100));
        assertTrue(dto.children().stream().anyMatch(c -> c.id() == 101));
    }

    @Test
    void toDtoShouldHandleMissingParents() {
        Person person = new Person(
                11,
                "Grogu",
                LocalDate.of(2020, 1, 1),
                0L,
                Set.of(),
                Set.of()
        );

        PersonDto dto = new PersonMapper().toDto(person);

        assertNull(dto.parent1());
        assertNull(dto.parent2());
    }
}
