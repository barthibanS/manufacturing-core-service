package org.sfs.dm.manufacturing_core_service.service;

import org.sfs.dm.manufacturing_core_service.entity.Person;
import org.sfs.dm.manufacturing_core_service.model.PersonModel;
import org.sfs.dm.manufacturing_core_service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<PersonModel> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public ResponseEntity<PersonModel> getPersonById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.map(p -> ResponseEntity.ok(toModel(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public PersonModel createPerson(PersonModel personModel) {
        Person person = toEntity(personModel);
        Person savedPerson = personRepository.save(person);
        return toModel(savedPerson);
    }

    public ResponseEntity<PersonModel> updatePerson(Long id, PersonModel personDetails) {
        Optional<Person> personOpt = personRepository.findById(id);
        if (personOpt.isPresent()) {
            Person person = personOpt.get();
            person.setFirstName(personDetails.getFirstName());
            person.setLastName(personDetails.getLastName());
            person.setYearOfBirth(personDetails.getYearOfBirth());
            Person updatedPerson = personRepository.save(person);
            return ResponseEntity.ok(toModel(updatedPerson));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deletePerson(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private PersonModel toModel(Person person) {
        if (person == null) return null;
        PersonModel model = new PersonModel();
        model.setId(person.getId());
        model.setFirstName(person.getFirstName());
        model.setLastName(person.getLastName());
        model.setYearOfBirth(person.getYearOfBirth());
        return model;
    }

    private Person toEntity(PersonModel model) {
        if (model == null) return null;
        Person entity = new Person();
        entity.setId(model.getId());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setYearOfBirth(model.getYearOfBirth());
        return entity;
    }
}