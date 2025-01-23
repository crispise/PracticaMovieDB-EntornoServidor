package com.esliceu.movies.repos;

import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.MovieCompany;
import com.esliceu.movies.models.MovieCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MovieCompanyRepo extends JpaRepository<MovieCompany, MovieCompanyId> {
}
