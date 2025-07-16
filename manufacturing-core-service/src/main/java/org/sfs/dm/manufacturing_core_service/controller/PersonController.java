package org.sfs.dm.manufacturing_core_service.controller;

import org.sfs.dm.manufacturing_core_service.entity.Person;
import org.sfs.dm.manufacturing_core_service.model.PersonModel;
import org.sfs.dm.manufacturing_core_service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<PersonModel> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonModel> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public PersonModel createPerson(@RequestBody PersonModel person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonModel> updatePerson(@PathVariable Long id, @RequestBody PersonModel person) {
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        return personService.deletePerson(id);
    }
}
