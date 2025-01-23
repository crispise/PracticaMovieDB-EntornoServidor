package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Gender;
import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieCast;
import com.esliceu.movies.models.MovieLanguages;
import com.esliceu.movies.services.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MovieCastController {
    @Autowired
    MovieServices movieServices;
    @Autowired
    MovieCastServices movieCastServices;
    @Autowired
    PersonServices personServices;
    @Autowired
    GenderServices genderServices;

    @GetMapping("/movieCast/{movieId}")
    public String getMovieCast (@PathVariable("movieId") Integer movieId, Model model) {
        String personJsonToSend = personServices.getPersonJson();
        model.addAttribute("jsonInfo", personJsonToSend);
        String genderJsondToSend = genderServices.getGenderJson();
        model.addAttribute("jsonInfo2", genderJsondToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<MovieCast> movieCast = movieCastServices.getMovieCast(movie);
        model.addAttribute("movieCast", movieCast);
        Integer minCastOrder = movieCastServices.getTheMinCastOrder(movie);
        System.out.println("castOrder");
        System.out.println(minCastOrder);
        model.addAttribute("minCastOrder", minCastOrder);
        return "movieCast";
    }

    @PostMapping("/addMovieCast")
    public String addMovieCast(@RequestParam Integer movieId,
                               @RequestParam String personName,
                               @RequestParam String gender,
                               @RequestParam String characterName,
                               @RequestParam Integer castOrder
                               , Model model) {
        String personJsonToSend = personServices.getPersonJson();
        model.addAttribute("jsonInfo", personJsonToSend);
        String genderJsondToSend = genderServices.getGenderJson();
        model.addAttribute("jsonInfo2", genderJsondToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<MovieCast> movieCast = movieCastServices.getMovieCast(movie);
        model.addAttribute("movieCast", movieCast);
        String message = movieCastServices.addMovieCast(personName,gender,characterName,castOrder,movie);
        if (message == null) {
            model.addAttribute("successMessage", "¡El personaje se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieCast/"+ movieId;
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
