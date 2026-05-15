package com.example._kfc.person.api;

import com.example._kfc.person.api.dtos.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class PersonControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    private final PersonDto han = new PersonDto(
            5L,
            "Han Solo",
            "1942-07-13",
            null,
            null,
            new PersonDto.RelatedPersonDto(6L),
            List.of(
                    new PersonDto.RelatedPersonDto(7L),
                    new PersonDto.RelatedPersonDto(8L),
                    new PersonDto.RelatedPersonDto(9L)
            )
    );

    private final PersonDto leia = new PersonDto(
            6L,
            "Leia Organa",
            "1942-07-13",
            null,
            null,
            new PersonDto.RelatedPersonDto(5L),
            List.of(
                    new PersonDto.RelatedPersonDto(7L),
                    new PersonDto.RelatedPersonDto(8L),
                    new PersonDto.RelatedPersonDto(9L)
            )
    );

    private final PersonDto ben = new PersonDto(
            7L,
            "Ben Solo",
            "1966-01-21",
            new PersonDto.RelatedPersonDto(5L),
            new PersonDto.RelatedPersonDto(6L),
            null,
            List.of()
    );

    private final PersonDto jacen = new PersonDto(
            8L,
            "Jacen Solo",
            "1967-01-21",
            new PersonDto.RelatedPersonDto(5L),
            new PersonDto.RelatedPersonDto(6L),
            null,
            List.of()
    );

    private final PersonDto jaina = new PersonDto(
            9L,
            "Jaina Solo",
            "2012-01-21",
            new PersonDto.RelatedPersonDto(5L),
            new PersonDto.RelatedPersonDto(6L),
            null,
            List.of()
    );

    @Test
    void shouldReturn444WhenStoredPeopleAreNotValid() {
        postPersonAndExpect444(han);
    }

    @Test
    void shouldReturn200ForPersonWithPartnerAnd3ChildrenOfWhichOneIsAMinor() {
        postPersonAndExpect444(han);
        postPersonAndExpect444(leia);
        postPersonAndExpect444(ben);
        postPersonAndExpect444(jacen);
        var responseJaina = postPersonAndExpect200(jaina);

        assertEquals(2, responseJaina.size());
        assertTrue(han.name().equals(responseJaina.get(0).name()) || han.name().equals(responseJaina.get(1).name()));
        assertTrue(leia.name().equals(responseJaina.get(0).name()) || han.name().equals(responseJaina.get(1).name()));
    }

    @Test
    void shouldReturn200ForPersonWithPartnerAnd3ChildrenOfWhichOneIsAMinorOrderVariant() {
        postPersonAndExpect444(jacen);
        postPersonAndExpect444(ben);
        postPersonAndExpect444(han);
        postPersonAndExpect200(jaina);
        postPersonAndExpect200(leia);
    }

    @Test
    void shouldReturn200ForPersonWithPartnerAnd3ChildrenOfWhichOneIsAMinorOrderVariant2() {
        postPersonAndExpect444(jacen);
        postPersonAndExpect444(ben);
        postPersonAndExpect444(jaina);
        var responseHan = postPersonAndExpect200(han);
        var responseLeia = postPersonAndExpect200(leia);

        assertEquals(2, responseHan.size());
        assertTrue(responseHan.get(0).name() == null || responseHan.get(1).name() == null);
        assertTrue(han.name().equals(responseHan.get(0).name()) || han.name().equals(responseHan.get(1).name()));
        assertNull(responseHan.get(1).name());
        assertEquals(2, responseLeia.size());
        assertTrue(han.name().equals(responseLeia.get(0).name()) || han.name().equals(responseLeia.get(1).name()));
        assertTrue(leia.name().equals(responseLeia.get(0).name()) || han.name().equals(responseLeia.get(1).name()));
    }

    @Test
    void shouldReturn444AfterBreakup() {
        var hanGoingSolo = new PersonDto(
                5L,
                "Han Solo",
                "1942-07-13",
                null,
                null,
                null, // <-- no partner :(
                List.of(
                        new PersonDto.RelatedPersonDto(7L),
                        new PersonDto.RelatedPersonDto(8L),
                        new PersonDto.RelatedPersonDto(9L)
                ));
        postPersonAndExpect444(han);
        postPersonAndExpect444(leia);
        postPersonAndExpect444(jacen);
        postPersonAndExpect444(ben);
        postPersonAndExpect200(jaina);
        postPersonAndExpect444(hanGoingSolo);
    }

    private void postPersonAndExpect444(PersonDto person) {
        restTestClient.post()
                .uri("/api/v1/people")
                .body(person)
                .exchange()
                .expectStatus().isEqualTo(444)
                .expectBody().isEmpty();
    }

    private static final ParameterizedTypeReference<List<PersonDto>> PERSON_LIST =
            new ParameterizedTypeReference<>() {
            };

    private List<PersonDto> postPersonAndExpect200(PersonDto person) {
        return restTestClient.post()
                .uri("/api/v1/people")
                .body(person)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PERSON_LIST)
                .returnResult()
                .getResponseBody();
    }
}