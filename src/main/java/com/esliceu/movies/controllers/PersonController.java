package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Person;
import com.esliceu.movies.services.CRUDServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PersonController {

    @Autowired
    CRUDServices crudServices;

    @GetMapping("/persons")
    public String getPersons(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> personPage = crudServices.findAllPersons(pageable);
        model.addAttribute("persons", personPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personPage.getTotalPages());
        model.addAttribute("size", size);
        String jsonToSend = crudServices.getJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "persons";
    }
}
