package com.esliceu.movies.controllers;

import com.esliceu.movies.models.*;
import com.esliceu.movies.services.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("minCastOrder", minCastOrder);
        return "movieCast";
    }

    @PostMapping("/addMovieCast")
    public String addMovieCast(@RequestParam Integer movieId,
                               @RequestParam String personName,
                               @RequestParam String gender,
                               @RequestParam String characterName,
                               @RequestParam Integer castOrder
                               ,RedirectAttributes redirectAttributes) {

        String message = movieCastServices.addMovieCast(personName,gender,characterName,castOrder,movieId);
        if (message == null) {
           redirectAttributes.addFlashAttribute("successMessage", "¡El personaje se ha añadido correctamente!");
        } else {
           redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:movieCast/"+ movieId;
    }

    @PostMapping("/deleteMovieCast")
    public String deleteMovieCast(@RequestParam Integer movieId,
                                  @RequestParam Integer genderId,
                                  @RequestParam String characterName,
                                  @RequestParam Integer personId,RedirectAttributes redirectAttributes) {

        String message = movieCastServices.deleteMovieCast(movieId, genderId, personId, characterName);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "¡El personaje se ha eliminado correctamente!");
        } else {
           redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:movieCast/"+ movieId;
    }




}
