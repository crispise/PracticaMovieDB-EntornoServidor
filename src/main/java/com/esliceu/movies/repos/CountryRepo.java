package com.esliceu.movies.repos;

import com.esliceu.movies.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepo extends JpaRepository<Country, Integer> {
    List<Country> findCountryByCountryName(String countryName);
}
