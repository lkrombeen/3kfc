package com.example._kfc.person.api;

import com.example._kfc.person.api.dtos.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class PersonControllerTest {

    @LocalServerPort
    private int port;

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
    void shouldReturn444WhenStoredPeopleAreNotValid() throws Exception {
        postPersonAndExpectStatus(han, 444);
    }

    @Test
    void shouldReturn200ForPersonWithPartnerAnd3ChildrenOfWhichOneIsAMinor() throws Exception {
        postPersonAndExpectStatus(han, 444);
        postPersonAndExpectStatus(leia, 444);
        postPersonAndExpectStatus(ben, 444);
        postPersonAndExpectStatus(jacen, 444);
        postPersonAndExpectStatus(jaina, 200);
    }

    @Test
    void shouldReturn200ForPersonWithPartnerAnd3ChildrenOfWhichOneIsAMinorOrderVariant() throws Exception {
        postPersonAndExpectStatus(jacen, 444);
        postPersonAndExpectStatus(ben, 444);
        postPersonAndExpectStatus(han, 444);
        postPersonAndExpectStatus(jaina, 200);
        postPersonAndExpectStatus(leia, 200);
    }

    @Test
    void shouldReturn200ForPersonWithPartnerAnd3ChildrenOfWhichOneIsAMinorOrderVariant2() throws Exception {
        postPersonAndExpectStatus(jacen, 444);
        postPersonAndExpectStatus(ben, 444);
        postPersonAndExpectStatus(jaina, 444);
        postPersonAndExpectStatus(han, 200);
        postPersonAndExpectStatus(leia, 200);
    }

    @Test
    void shouldReturn444AfterBreakup() throws Exception {
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
        postPersonAndExpectStatus(han, 444);
        postPersonAndExpectStatus(leia, 444);
        postPersonAndExpectStatus(jacen, 444);
        postPersonAndExpectStatus(ben, 444);
        postPersonAndExpectStatus(jaina, 200);
        postPersonAndExpectStatus(hanGoingSolo, 444);
    }

    private void postPersonAndExpectStatus(PersonDto person, int expectedStatus) throws Exception {
        restTestClient.post()
                .uri("http://localhost:%d/api/v1/people".formatted(port))
                .body(person)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }
}