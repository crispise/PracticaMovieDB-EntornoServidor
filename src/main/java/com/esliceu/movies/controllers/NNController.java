package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.services.MovieServices;
import com.esliceu.movies.services.PCompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NNController {
    @Autowired
    MovieServices movieServices;
    @Autowired
    PCompanyServices pCompanyServices;

    @GetMapping("/movieCompany/{movieId}")
    public String getMovieCompany(@PathVariable("movieId") Integer movieId, Model model) {
        String jsonToSend = pCompanyServices.getCompaniesJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<ProductionCompany> movieCompanies = movieServices.getMovieCompanies(movie);
        model.addAttribute("movieCompanies", movieCompanies);
        return "movieCompanies";
    }

    @PostMapping("/addMovieCompany")
    public String addMovieCompany(@RequestParam Integer movieId,
                                  @RequestParam String companyName, Model model) {
        String jsonToSend = pCompanyServices.getCompaniesJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<ProductionCompany> movieCompanies = movieServices.getMovieCompanies(movie);
        model.addAttribute("movieCompanies", movieCompanies);

        String message = movieServices.addMovieCompany(companyName, movie);
        if (message == null) {
            model.addAttribute("successMessage", "¡La compañia se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieCompany/"+movieId;
    }

    @PostMapping("/deleteMovieCompany")
    public String deleteMovieCompany(@RequestParam Integer movieId,
                                  @RequestParam String companyName, Model model) {
        String jsonToSend = pCompanyServices.getCompaniesJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<ProductionCompany> movieCompanies = movieServices.getMovieCompanies(movie);
        model.addAttribute("movieCompanies", movieCompanies);

        String message = movieServices.addMovieCompany(companyName, movie);
        if (message == null) {
            model.addAttribute("successMessage", "¡La compañia se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieCompany/"+movieId;
    }



}
