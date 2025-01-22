package com.esliceu.movies.controllers;
import com.esliceu.movies.models.Country;
import com.esliceu.movies.services.CountryServices;
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
public class CountryController {

    @Autowired
    CountryServices countryServices;

    @GetMapping("/countries")
    public String getCountries() {
           return "countries";
    }

    @PostMapping("/countries")
    public String newCountry(@RequestParam String actionSelect, Model model){
        switch (actionSelect) {
            case "view-all":
               return "redirect:/allCountries";
            case "search-by-name":
                return "redirect:/searchCountries";
            case "create-new":
                return "redirect:/createCountry";
            default:
                model.addAttribute("error", "Acción no reconocida");
                break;
        }
        return "countries";
    }

    @GetMapping("/allCountries")
    public String getAllCountries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, Model model) {
        model.addAttribute("viewAll", true);
        Pageable pageable = PageRequest.of(page, size);
        Page<Country> countryPage = countryServices.findAllCountries(pageable);
        model.addAttribute("countries", countryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", countryPage.getTotalPages());
        model.addAttribute("size", size);
        return "countries";
    }

    @GetMapping("/searchCountries")
    public String searchCountries(Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = countryServices.getCountryJson();
        model.addAttribute("jsonInfo", jsonToSend);
        return "countries";
    }

    @PostMapping("/searchCountries")
    public String getCountryWanted(@RequestParam String countryName, Model model) {
        model.addAttribute("searchByName", true);
        String jsonToSend = countryServices.getCountryJson();
        model.addAttribute("jsonInfo", jsonToSend);
        List<Country> countriesFound = countryServices.findCountriesByName(countryName);
        model.addAttribute("countriesFound", countriesFound);
        return "countries";
    }

    @GetMapping("/createCountry")
    public String createCountry(Model model) {
        model.addAttribute("createNew", true);
        return "countries";
    }

    @PostMapping("/createCountry")
    public String saveCountry(@RequestParam String countryName, @RequestParam String countryIsoCode, Model model) {
       model.addAttribute("createNew", true);
        String resultMessage =countryServices.saveCountry(countryName, countryIsoCode);
        if (resultMessage == null) {
            model.addAttribute("successMessage", "¡País creado correctamente!");
        } else {
            model.addAttribute("errorMessage", resultMessage);
        }
        return "countries";
    }

    @PostMapping("/deleteCountry")
    public String deleteCountry(@RequestParam Integer countryId, Model model){
        String message = countryServices.deleteCountry(countryId);
        if (message.equals("Ok")){
            model.addAttribute("successMessage", "El país se ha eliminado correctamente");
        }else {
            model.addAttribute("errorMessage", "Ha habido un error al eliminar el país");
        }
        return "countries";
    }

    @GetMapping("/updateCountry/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Country country = countryServices.findCountryById(id);
        if (country != null) {
            model.addAttribute("country", country);
            model.addAttribute("update", true);
            return "countries";
        } else {
            model.addAttribute("errorMessage", "El país no fue encontrado");
            return "countries";
        }
    }

   @PostMapping("/updateCountry/{countryId}")
    public String updateCountry(@RequestParam Integer countryId, @RequestParam String countryName,
                                @RequestParam String countryIsoCode ,Model model){
        Country countryUpdate = countryServices.updateCountry(countryId, countryName, countryIsoCode);
        model.addAttribute("country", countryUpdate);
        model.addAttribute("update", true);
       return "countries";
   }


}
