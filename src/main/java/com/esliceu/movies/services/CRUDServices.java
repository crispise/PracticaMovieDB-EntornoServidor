package com.esliceu.movies.services;

import com.esliceu.movies.models.Person;
import com.esliceu.movies.repos.PersonRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CRUDServices {
    @Autowired
    PersonRepo personRepo;


    public Page<Person> findAllPersons(Pageable pageable) {
        return personRepo.findAll(pageable);
    }

    public String getJson() {
        List<Person> persons = personRepo.findAll();
        List<String> nombres = persons.stream()
                .map(p -> p.getPersonName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String resultado = gson.toJson(nombres);
        return resultado;
    }

    public String savePerson(String personName) {
        if (personName == null || personName.trim().isEmpty()) {
            return "El nombre de la persona no puede estar vacío.";
        }
        if (personRepo.findPersonByPersonName(personName).size() > 1) {
            return "Ya existe una persona con ese nombre.";
        }
        Person person = new Person();
        person.setPersonName(personName);
        personRepo.save(person);
        return null;
    }

    public Person findPersonById(Integer personId) {
        return personRepo.findById(personId).get();
    }

    public List<Person> findPersonsByName(String personSearch) {
        return personRepo.findPersonByPersonName(personSearch);
    }
}
