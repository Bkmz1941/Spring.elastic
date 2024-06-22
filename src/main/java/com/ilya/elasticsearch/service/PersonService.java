package com.ilya.elasticsearch.service;

import com.ilya.elasticsearch.document.Person;
import com.ilya.elasticsearch.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void save(final Person person) {
        this.personRepository.save(person);
    }

    public Person findById(final String id) {
        return this.personRepository.findById(id).orElse(null);
    }
}
