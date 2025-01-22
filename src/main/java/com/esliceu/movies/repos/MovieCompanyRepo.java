package com.esliceu.movies.repos;

import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.MovieCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieCompanyRepo extends JpaRepository<MovieCompany, Integer> {
}
