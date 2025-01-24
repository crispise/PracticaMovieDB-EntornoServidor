package com.esliceu.movies.controllers;
import com.esliceu.movies.models.Country;
import com.esliceu.movies.services.CountryServices;
import com.esliceu.movies.services.QueriesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QueriesController {

    @Autowired
    QueriesServices queriesServices;

    @GetMapping("/selectQueries")
    public String getQuerie() {
           return "queries";
    }

    @PostMapping("/selectQueries")
    public String getPage(@RequestParam String actionSelect, Model model) {
        String jsonInfo = queriesServices.getJson(actionSelect);
        model.addAttribute("jsonInfo", jsonInfo);
        return "queries";
    }




}
