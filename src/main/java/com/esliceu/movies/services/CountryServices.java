package com.esliceu.movies.services;
import com.esliceu.movies.models.Country;
import com.esliceu.movies.repos.CountryRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServices {
    @Autowired
    CountryRepo countryRepo;

    public Page<Country> findAllCountries(Pageable pageable) {
        return countryRepo.findAll(pageable);
    }

    public String getCountryJson() {
        List<Country> countries = countryRepo.findAll();
        List<String> names = countries.stream()
                .map(p -> p.getCountryName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public String saveCountry(String name, String isoCode) {
        if (name == null || name.trim().isEmpty()) {
            return "El nombre del país no puede estar vacío.";
        }
        if (isoCode == null || isoCode.trim().isEmpty()) {
            return "El código iso no puede estar vacío.";
        }
        if (countryRepo.findCountryByCountryName(name).size() > 1) {
            return "Ya existe un país con ese nombre.";
        }
        Country country = new Country();
        country.setCountryName(name);
        country.setCountryIsoCode(isoCode);
        countryRepo.save(country);
        return null;
    }


    public Country findCountryById(Integer id) {
        return countryRepo.findById(id).get();
    }

    public List<Country> findCountriesByName(String countrySearch) {
        return countryRepo.findCountryByCountryName(countrySearch);
    }

    public String deleteCountry(Integer id) {
        try {
            countryRepo.deleteById(id);
            return "Ok";
        }catch (Exception e) {
            return "Error";
        }

    }

    public Country updateCountry(Integer id, String name, String isoCode) {
        Optional<Country> existingCountry = countryRepo.findById(id);
        if (existingCountry.isPresent()) {
            Country updatedCountry = existingCountry.get();
            if (name != null && !name.isEmpty()) {
                updatedCountry.setCountryName(name);
            }
            if (isoCode != null && !isoCode.isEmpty()) {
                updatedCountry.setCountryIsoCode(isoCode);
            }
            countryRepo.save(updatedCountry);
            return updatedCountry;
        } else {
            return null;
        }
    }

}
