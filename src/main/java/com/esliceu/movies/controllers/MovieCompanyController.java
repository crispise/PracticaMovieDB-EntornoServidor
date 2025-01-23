package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieCompany;
import com.esliceu.movies.models.MovieCompanyId;
import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.services.MovieCompanyServices;
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
public class MovieCompanyController {
    @Autowired
    MovieServices movieServices;
    @Autowired
    MovieCompanyServices movieCompanyServices;
    @Autowired
    PCompanyServices pCompanyServices;

    @GetMapping("/movieCompany/{movieId}")
    public String getMovieCompany(@PathVariable("movieId") Integer movieId, Model model) {
        String jsonToSend = pCompanyServices.getCompaniesJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<MovieCompany> movieCompanies = movieCompanyServices.getMovieCompanies(movie);
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
        List<MovieCompany> movieCompanies = movieCompanyServices.getMovieCompanies(movie);
        model.addAttribute("movieCompanies", movieCompanies);

        String message = movieCompanyServices.addMovieCompany(companyName, movie);
        if (message == null) {
            model.addAttribute("successMessage", "¡La compañia se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieCompany/"+movieId;
    }


    @PostMapping("/deleteMovieCompany")
    public String deleteMovieCompany(@RequestParam Integer movieId,
                                     @RequestParam Integer companyId, Model model) {
        String jsonToSend = pCompanyServices.getCompaniesJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<MovieCompany> movieCompanies = movieCompanyServices.getMovieCompanies(movie);
        model.addAttribute("movieCompanies", movieCompanies);
        String message = movieCompanyServices.deleteMovieCompany(movieId, companyId);
        if (message == null) {
            model.addAttribute("successMessage", "¡La compañia se ha eliminado correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:movieCompany/"+movieId;
    }



}
