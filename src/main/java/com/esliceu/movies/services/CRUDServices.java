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
}
