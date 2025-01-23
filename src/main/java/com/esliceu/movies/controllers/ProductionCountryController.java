package com.esliceu.movies.controllers;

import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieCompany;
import com.esliceu.movies.models.ProductionCountry;
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
public class ProductionCountryController {
    @Autowired
    MovieServices movieServices;
    @Autowired
    ProductionCountryServices productionCountryServices;
    @Autowired
    CountryServices countryServices;

    @GetMapping("/productionCountry/{movieId}")
    public String getProductionCountry(@PathVariable("movieId") Integer movieId, Model model) {
        String jsonToSend = countryServices.getCountryJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<ProductionCountry> productionCountries = productionCountryServices.getProductionCountries(movie);
        model.addAttribute("productionCountries", productionCountries);
        return "productionCountries";
    }

    @PostMapping("/addProductionCountry")
    public String addMovieCompany(@RequestParam Integer movieId,
                                  @RequestParam String countryName, Model model) {
        String jsonToSend = countryServices.getCountryJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<ProductionCountry> productionCountries = productionCountryServices.getProductionCountries(movie);
        model.addAttribute("productionCountries", productionCountries);

        String message = productionCountryServices.addProductionCountry(countryName, movie);
        if (message == null) {
            model.addAttribute("successMessage", "¡El país se ha añadido correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:productionCountry/"+movieId;
    }

    /*
    @PostMapping("/deleteMovieCompany")
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
    }

*/

}
