package com.esliceu.movies.repos;

import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
    List<Movie> findMovieByTitle(String title);
    List<Movie> findDistinctByMovieCastPersonPersonName(String personName);
    List<Movie> findDistinctByMovieCastIdCharacterName(String characterName);
    List<Movie> findDistinctByMovieGenresGenreGenreName(String genreName);
    List<Movie> findDistinctByMovieCrewPersonPersonNameAndMovieCrewIdJob(String personName, String job);
}
