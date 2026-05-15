package com.example._kfc.person.mappers;

import com.example._kfc.person.api.dtos.PersonDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonMapperTest {
    @Test
    void DtoShouldMapAllFieldsCorrectly() {
        var dto = new PersonDto(
                1,
                "John Doe",
                "1990-01-01",
                new PersonDto.RelatedPersonDto(10),
                new PersonDto.RelatedPersonDto(11),
                new PersonDto.RelatedPersonDto(12),
                List.of(
                        new PersonDto.RelatedPersonDto(100),
                        new PersonDto.RelatedPersonDto(101)
                )
        );

        var result = PersonMapper.FromDto(dto);

        assertEquals(1, result.id());
        assertEquals("John Doe", result.name());
        assertEquals("1990-01-01", result.birthDate());
        assertEquals(10, result.parent1Id());
        assertEquals(11, result.parent2Id());
        assertEquals(12, result.partnerId());
        assertEquals(Set.of(100, 101), result.childrenIds());
    }
}