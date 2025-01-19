package com.esliceu.movies.services;

import com.esliceu.movies.models.Person;
import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.repos.PCompaniesRepo;
import com.esliceu.movies.repos.PersonRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServices {
    @Autowired
    PersonRepo personRepo;

    public Page<Person> findAllPersons(Pageable pageable) {
        return personRepo.findAll(pageable);
    }

    public String getPersonJson() {
        List<Person> persons = personRepo.findAll();
        List<String> names = persons.stream()
                .map(p -> p.getPersonName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public String savePerson(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El nombre de la persona no puede estar vacío.";
        }
        if (personRepo.findPersonByPersonName(name).size() > 1) {
            return "Ya existe una persona con ese nombre.";
        }
        Person person = new Person();
        person.setPersonName(name);
        personRepo.save(person);
        return null;
    }


    public Person findPersonById(Integer id) {
        return personRepo.findById(id).get();
    }

    public List<Person> findPersonsByName(String personSearch) {
        return personRepo.findPersonByPersonName(personSearch);
    }

    public String deletePerson(Integer id) {
        try {
            personRepo.deleteById(id);
            return "Ok";
        }catch (Exception e) {
            return "Error";
        }

    }

    public Person updatePerson(Integer id, String name) {
        Optional<Person> existingPerson = personRepo.findById(id);
        if (existingPerson.isPresent()) {
            Person updatedPerson = existingPerson.get();
            updatedPerson.setPersonName(name);
            personRepo.save(updatedPerson);
            return updatedPerson;
        } else {
            return null;
        }
    }

}
