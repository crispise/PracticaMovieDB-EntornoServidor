package com.esliceu.movies.controllers;
import com.esliceu.movies.models.Country;
import com.esliceu.movies.services.CountryServices;
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
public class InitController {

    @GetMapping("/init")
    public String getinitPage(){
        return "init";
    }

    @PostMapping("/init")
    public String getEntity (@RequestParam String entityManagment) {
        return switch (entityManagment) {
            case "movie" -> "redirect:/movies";
            case "production_company" -> "redirect:/productionCompany";
            case "departments" -> "redirect:/departments";
            case "genders" -> "redirect:/genders";
            case "movie_genres" -> "redirect:/genres";
            case "languages" -> "redirect:/languages";
            case "countries" -> "redirect:/countries";
            case "keywords" -> "redirect:/keywords";
            case "people" -> "redirect:/persons";
            case "language_roles" -> "redirect:/languageRoles";
            default -> "redirect:/";
        };
    }
}
