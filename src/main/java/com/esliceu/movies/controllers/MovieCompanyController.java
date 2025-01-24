package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieCompany;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                                  @RequestParam String companyName, RedirectAttributes redirectAttributes) {

        String message = movieCompanyServices.addMovieCompany(companyName, movieId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "¡La compañia se ha añadido correctamente!");
        } else {
           redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:movieCompany/"+movieId;
    }


    @PostMapping("/deleteMovieCompany")
    public String deleteMovieCompany(@RequestParam Integer movieId,
                                     @RequestParam Integer companyId, RedirectAttributes redirectAttributes) {
        String message = movieCompanyServices.deleteMovieCompany(movieId, companyId);
        if (message == null) {
            redirectAttributes.addFlashAttribute("successMessage", "¡La compañia se ha eliminado correctamente!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", message);
        }
        return "redirect:movieCompany/"+movieId;
    }



}
