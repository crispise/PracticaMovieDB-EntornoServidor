package com.esliceu.movies.repos;

import com.esliceu.movies.models.MovieGenres;
import com.esliceu.movies.models.MovieGenresId;
import com.esliceu.movies.models.MovieLanguages;
import com.esliceu.movies.models.MovieLanguagesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieLanguagesRepo extends JpaRepository<MovieLanguages, MovieLanguagesId> {
}
