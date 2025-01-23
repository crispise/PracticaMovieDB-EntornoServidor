package com.esliceu.movies.repos;

import com.esliceu.movies.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCrewRepo extends JpaRepository<MovieCrew, MovieCrewId> {
    Page<MovieCrew> findByMovie(Movie movie, Pageable pageable);
}
