package com.example._kfc.person.api;

import com.example._kfc.person.api.dtos.PersonDto;
import com.example._kfc.person.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<List<PersonDto>> upsertPerson(@RequestBody PersonDto dto) {
        personService.UpsertPerson(dto);
        var result = personService.GetValidPeople();

        if (result.isEmpty()) {
            return ResponseEntity.status(444).build();
        } else {
            return ResponseEntity.ok(result);
        }
    }
}