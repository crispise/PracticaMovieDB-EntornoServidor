package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.CountryRepo;
import com.esliceu.movies.repos.ProductionCountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionCountryServices {
    @Autowired
    CountryRepo countryRepo;
    @Autowired
    ProductionCountryRepo productionCountryRepo;


    public List<ProductionCountry> getProductionCountries(Movie movie) {
        List<ProductionCountry> productionCountries = movie.getProductionCountries();
        return productionCountries;
    }

    public String addProductionCountry(String countryName, Movie movie) {
        List<Country> countries = countryRepo.findCountryByCountryName(countryName);
        if (countries.size() == 1) {
            Country country = countries.get(0);
            boolean exists = false;
            for (ProductionCountry pc : movie.getProductionCountries()) {
                if (pc.getCountry().getCountryId().equals(country.getCountryId())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                return "Este país ya está añadido";
            }
            return createAndSaveProductionCountry(movie, country);
        }
        return "Hay más de una compañia con ese nombre";
    }

    private String createAndSaveProductionCountry(Movie movie, Country country) {
        ProductionCountry  productionCountry = new ProductionCountry();
        productionCountry.setMovie(movie);
        productionCountry.setCountry(country);

        ProductionCountryId productionCountryId = new ProductionCountryId();
        productionCountryId.setCountryId(country.getCountryId());
        productionCountryId.setMovieId(movie.getMovieId());
        productionCountry.setId(productionCountryId);
        productionCountryRepo.save(productionCountry);
        return null;
    }

    public String deleteProductionCountry(Integer movieId, Integer countryId) {
        ProductionCountryId productionCountryId = new ProductionCountryId();
        productionCountryId.setMovieId(movieId);
        productionCountryId.setCountryId(countryId);

        Optional<ProductionCountry> productionCountry = productionCountryRepo.findById(productionCountryId);
        if (productionCountry.isPresent()) {
            productionCountryRepo.deleteById(productionCountryId);
            return null;
        } else {
            return "Ese país de producción no está relacionado con la película";
        }
    }
}
