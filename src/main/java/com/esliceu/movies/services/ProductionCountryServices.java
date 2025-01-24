package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.CountryRepo;
import com.esliceu.movies.repos.MovieRepo;
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
    @Autowired
    MovieRepo movieRepo;


    public List<ProductionCountry> getProductionCountries(Movie movie) {
        List<ProductionCountry> productionCountries = movie.getProductionCountries();
        return productionCountries;
    }

    public String addProductionCountry(String countryName, Integer movieId) {
        if (countryName.isEmpty()) return "Falta introducir el nombre del pais";
        Movie movie = movieRepo.findById(movieId).get();
        List<Country> countries = countryRepo.findCountryByCountryName(countryName);
        if (countries.size() > 1) {
            return "Hay más de un país con ese nombre";
        } else if (countries.isEmpty()) {
            return "No hay ningún país con ese nombre";
        }
        Country country = countries.get(0);
        return createKeyAndProductionCountry(movie, country);
    }

    private String createKeyAndProductionCountry(Movie movie, Country country) {

        ProductionCountryId productionCountryId = new ProductionCountryId();
        productionCountryId.setCountryId(country.getCountryId());
        productionCountryId.setMovieId(movie.getMovieId());
        Optional<ProductionCountry> productionCountrySearch = productionCountryRepo.findById(productionCountryId);
        if (productionCountrySearch.isPresent()){
            return "Este registro ya está en la lista";
        }
        saveProductionCountry(movie, country, productionCountryId);
        return null;
    }

    private void saveProductionCountry(Movie movie, Country country, ProductionCountryId productionCountryId) {
        ProductionCountry  productionCountry = new ProductionCountry();
        productionCountry.setMovie(movie);
        productionCountry.setCountry(country);
        productionCountry.setId(productionCountryId);
        productionCountryRepo.save(productionCountry);
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
