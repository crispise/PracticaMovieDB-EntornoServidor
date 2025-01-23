package com.esliceu.movies.controllers;
import com.esliceu.movies.models.LanguageRole;
import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieLanguages;
import com.esliceu.movies.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MovieLanguagesController {
    @Autowired
    MovieServices movieServices;
    @Autowired
    MovieLanguagesServices movieLanguagesServices;
    @Autowired
    LanguageServices languageServices;
    @Autowired
    LanguageRoleServices languageRoleServices;

    @GetMapping("/movieLanguage/{movieId}")
    public String getMovieLanguages (@PathVariable("movieId") Integer movieId, Model model) {
        String languageJsonToSend = languageServices.getLanguageJson();
        model.addAttribute("jsonInfo", languageJsonToSend);
        String languageRoleJsondToSend = languageRoleServices.getLanguageRoleJson();
        model.addAttribute("jsonInfo2", languageRoleJsondToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<MovieLanguages> movieLanguages = movieLanguagesServices.getMovieLanguages(movie);
        model.addAttribute("movieLanguages", movieLanguages);
        return "movieLanguages";
    }

    @PostMapping("/addMovieLanguage")
    public String addMovieLanguage(@RequestParam Integer movieId,
                                  @RequestParam String languageName,
                                  @RequestParam String languageRole, Model model) {
        String jsonToSend = languageServices.getLanguageJson();
        model.addAttribute("jsonInfo", jsonToSend);
        String languageRoleJsondToSend = languageRoleServices.getLanguageRoleJson();
        model.addAttribute("jsonInfo2", languageRoleJsondToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<MovieLanguages> movieLanguages = movieLanguagesServices.getMovieLanguages(movie);
        model.addAttribute("movieLanguages", movieLanguages);

        String message = movieLanguagesServices.addMovieLanguage(languageName, movie, languageRole);
        if (message == null) {
            model.addAttribute("successMessage", "¡El idioma se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieLanguage/"+ movieId;
    }

    /*@PostMapping("/deleteMovieCompany")
    public String deleteMovieCompany(@RequestParam Integer movieId,
                                  @RequestParam Integer companyId, Model model) {
        String jsonToSend = pCompanyServices.getCompaniesJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<ProductionCompany> movieCompanies = movieCompanyServices.getMovieCompanies(movie);
        model.addAttribute("movieCompanies", movieCompanies);
        String message = movieCompanyServices.deleteMovieCompany(companyId, movieId);
        if (message == null) {
            model.addAttribute("successMessage", "¡La compañia se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieCompany/"+movieId;
    }*/



}
