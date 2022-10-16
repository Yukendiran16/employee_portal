package net.javaguides.springboot.service;

import net.javaguides.springboot.PersonRepository;
import net.javaguides.springboot.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public Person getPersonById(int personId) {
        return personRepository.findById(personId).get();
    }

    public void deletePersonById(int personId) {
        personRepository.deleteById(personId);
    }

    public Person getPerson(int persondId) {
        return personRepository.findById(persondId).get();
    }
}
