package com.example._kfc.person.entities;

import com.example._kfc.person.domain.valueobjects.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    void testHasPartner() {
        var bachelor = new Person.PersonBuilder().id(1).name("Han Solo").birthDate("1942-07-13").build();

        assertFalse(bachelor.HasPartner());
    }
}