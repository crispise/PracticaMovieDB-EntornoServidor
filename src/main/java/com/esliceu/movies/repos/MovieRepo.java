package com.esliceu.movies.repos;

import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
    Page<Movie> findMovieByTitle(String title, Pageable pageable);
    Page<Movie> findByTitleContaining(String condition, Pageable pageable);

    Page<Movie> findDistinctByMovieCastPersonPersonName(String personName, Pageable pageable);
    Page<Movie> findDistinctByMovieCastPersonPersonNameContaining(String personName, Pageable pageable);

    Page<Movie> findDistinctByMovieCastIdCharacterName(String characterName, Pageable pageable);
    Page<Movie> findDistinctByMovieCastIdCharacterNameContaining(String characterName, Pageable pageable);

    Page<Movie> findDistinctByMovieGenresGenreGenreName(String genreName, Pageable pageable);
    Page<Movie> findDistinctByMovieGenresGenreGenreNameContaining(String genreName, Pageable pageable);

    Page<Movie> findDistinctByMovieCrewPersonPersonNameAndMovieCrewIdJob(String directorName, String job, Pageable pageable);
    Page<Movie> findDistinctByMovieCrewPersonPersonNameContainingAndMovieCrewIdJob(String directorName, String job, Pageable pageable);

}
