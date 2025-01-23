package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieCast;
import com.esliceu.movies.models.MovieCrew;
import com.esliceu.movies.services.*;
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
public class MovieCrewController {
    @Autowired
    MovieServices movieServices;
    @Autowired
    MovieCrewServices movieCrewServices;
    @Autowired
    PersonServices personServices;
    @Autowired
    DepartmentServices departmentServices;

    @GetMapping("/movieCrew/{movieId}")
    public String getMovieCrew (@PathVariable("movieId") Integer movieId,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int size, Model model) {
        String personJsonToSend = personServices.getPersonJson();
        model.addAttribute("jsonInfo", personJsonToSend);
        String departmentJsondToSend = departmentServices.getDepartmentJson();
        model.addAttribute("jsonInfo2", departmentJsondToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        Pageable pageable = PageRequest.of(page, size);
        Page<MovieCrew> movieCrew = movieCrewServices.getMovieCrew(pageable, movie);
        model.addAttribute("movieCrew", movieCrew.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", movieCrew.getTotalPages());
        model.addAttribute("size", size);

        return "movieCrew";
    }

    @PostMapping("/addMovieCrew")
    public String addMovieCast(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               @RequestParam Integer movieId,
                               @RequestParam String personName,
                               @RequestParam String departmentName,
                               @RequestParam String job
                               , Model model) {
        String personJsonToSend = personServices.getPersonJson();
        model.addAttribute("jsonInfo", personJsonToSend);
        String departmentJsondToSend = departmentServices.getDepartmentJson();
        model.addAttribute("jsonInfo2", departmentJsondToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        Pageable pageable = PageRequest.of(page, size);
        Page<MovieCrew> movieCrew = movieCrewServices.getMovieCrew(pageable, movie);
        model.addAttribute("movieCrew", movieCrew.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", movieCrew.getTotalPages());
        model.addAttribute("size", size);
        String message = movieCrewServices.addMovieCrew(personName,departmentName,job,movie);
        System.out.println("el mensaje es");
        System.out.println(message);
        if (message == null) {
            model.addAttribute("successMessage", "¡El miembro del equipo se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieCrew/"+ movieId;
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
