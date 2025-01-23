package com.esliceu.movies.repos;

import com.esliceu.movies.models.MovieCast;
import com.esliceu.movies.models.MovieCastId;
import com.esliceu.movies.models.MovieGenres;
import com.esliceu.movies.models.MovieGenresId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCastRepo extends JpaRepository<MovieCast, MovieCastId> {
}
