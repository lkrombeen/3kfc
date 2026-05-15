package com.example._kfc.person.domain;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PeopleRecordTest {

    @Test
    void getOrCreateShouldReturnSameInstanceForSameId() {
        var record = new PeopleRecord();

        var p1 = record.GetOrCreate(1);
        var p2 = record.GetOrCreate(1);

        assertSame(p1, p2);
        assertEquals(1, p1.getId());
    }

    @Test
    void removeExistingRelationsShouldRemovePartnerParentAndChildLinks() {
        // Arrange
        var record = new PeopleRecord();

        var parent = record.GetOrCreate(1);
        var child = record.GetOrCreate(2);
        var partner = record.GetOrCreate(3);
        var person = record.GetOrCreate(4);

        person.setParentIds(Set.of(1L));
        person.setChildrenIds(Set.of(2L));
        person.setPartnerId(3L);

        parent.getChildrenIds().add(4L);
        child.getParentIds().add(4L);
        partner.setPartnerId(4L);

        // Act
        record.RemoveExistingRelationsOf(person);

        // Assert
        assertNull(partner.getPartnerId());
        assertFalse(parent.getChildrenIds().contains(4L));
        assertFalse(child.getParentIds().contains(4L));
    }

    @Test
    void addRelationsShouldAddPartnerParentAndChildLinks() {
        // Arrange
        var record = new PeopleRecord();

        var parent = record.GetOrCreate(1);
        var child = record.GetOrCreate(2);
        var partner = record.GetOrCreate(3);
        var person = record.GetOrCreate(4);

        person.setParentIds(Set.of(1L));
        person.setChildrenIds(Set.of(2L));
        person.setPartnerId(3L);

        // Act
        record.AddRelationsOf(person);

        // Assert
        assertEquals(4, partner.getPartnerId());
        assertTrue(parent.getChildrenIds().contains(4L));
        assertTrue(child.getParentIds().contains(4L));
    }

    @Test
    void removeThenAddRelationsShouldRestoreSymmetry() {
        // Arrange
        var record = new PeopleRecord();

        // Act
        var parent = record.GetOrCreate(1);
        var child = record.GetOrCreate(2);
        var partner = record.GetOrCreate(3);
        var person = record.GetOrCreate(4);

        person.setParentIds(Set.of(1L));
        person.setChildrenIds(Set.of(2L));
        person.setPartnerId(3L);

        record.AddRelationsOf(person);
        record.RemoveExistingRelationsOf(person);
        record.AddRelationsOf(person);

        // Assert
        assertEquals(4, partner.getPartnerId());
        assertTrue(parent.getChildrenIds().contains(4L));
        assertTrue(child.getParentIds().contains(4L));
    }
}
