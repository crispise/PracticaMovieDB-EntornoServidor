package com.esliceu.movies.services;

import com.esliceu.movies.models.Person;
import com.esliceu.movies.repos.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CRUDServices {
    @Autowired
    PersonRepo personRepo;


    public Page<Person> findAllPersons(Pageable pageable) {
        return personRepo.findAll(pageable);
    }

    public void getJson(Page<Person> personPage) {


    }
}
