package com.esliceu.movies.controllers;

import com.esliceu.movies.models.*;
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

    @PostMapping("/deleteProductionCountry")
    public String deleteProductionCountry(@RequestParam Integer movieId,
                                   @RequestParam Integer countryId, Model model) {
        String jsonToSend = countryServices.getCountryJson();
        model.addAttribute("jsonInfo", jsonToSend);
        Movie movie = movieServices.findMovieById(movieId);
        model.addAttribute("movie", movie);
        List<ProductionCountry> productionCountries = productionCountryServices.getProductionCountries(movie);
        model.addAttribute("productionCountries", productionCountries);

        String message = productionCountryServices.deleteProductionCountry(movieId, countryId);
        if (message == null) {
            model.addAttribute("successMessage", "¡El país de producción se ha eliminado correctamente!");
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "redirect:productionCountry/"+movieId;
    }

}
