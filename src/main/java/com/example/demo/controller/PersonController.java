package com.example.demo.controller;

import com.example.demo.ResourceNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/persons")
    public Person addPerson(@RequestBody Person person) {

        System.out.println("Persona guardada en la DB");
        return personRepository.save(person);

    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons() {

        System.out.println("Todas las personas han sido recuperadas de la DB");
        return ResponseEntity.ok(personRepository.findAll());

    }

    @GetMapping("persons/{personId}")
    @Cacheable(value = "persons",key = "#personId")
    public Person findPersonById(@PathVariable(value = "personId") Integer personId) {
        System.out.println("Persona recuperada de la DB:: "+ personId);
        return personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not found: " + personId));

    }

    @PutMapping("persons/{personId}")
    @CachePut(value = "persons", key = "#personId")
    public Person updatePerson(@PathVariable(value = "personId") Integer personId, @RequestBody Person personDetails) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID :: " + personId));
        person.setNombres(personDetails.getNombres());
        person.setNumCedula(personDetails.getNumCedula());

        final Person updatedPerson = personRepository.save(person);
        return updatedPerson;

    }

}
