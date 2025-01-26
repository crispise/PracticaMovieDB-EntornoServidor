package com.esliceu.movies.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EntityController {

    @GetMapping("/manage/{entity}")
    public String getinitPage(@PathVariable("entity") String entity){
        return switch (entity) {
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
