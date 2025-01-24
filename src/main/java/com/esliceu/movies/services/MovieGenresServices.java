package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.GenreRepo;
import com.esliceu.movies.repos.MovieGenresRepo;
import com.esliceu.movies.repos.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieGenresServices {
    @Autowired
    GenreRepo  genreRepo;
    @Autowired
    MovieGenresRepo movieGenreRepo;
    @Autowired
    MovieRepo movieRepo;


    public List<MovieGenres> getMovieGenres(Movie movie) {
        List<MovieGenres> movieGenres = movie.getMovieGenres();
        return movieGenres;
    }

    public String addMovieGenre (String genreName, Integer movieId) {
        if (genreName.isEmpty())return "Falta introducir el género";
        Movie movie = movieRepo.findById(movieId).get();
        List<Genre> genres = genreRepo.findGenreByGenreName(genreName);
        if (genres.size() > 1) {
            return "Hay más de un género con ese nombre";
        } else if (genres.isEmpty()) {
            return "No hay géneros con ese nombre";
        }
        Genre genre = genres.get(0);
        return createKeyAndMovieGenre(movie, genre);
    }

    private String createKeyAndMovieGenre(Movie movie, Genre genre) {
        MovieGenresId mgi = new MovieGenresId();
        mgi.setGenreId(genre.getGenreId());
        mgi.setMovieId(movie.getMovieId());
        Optional<MovieGenres> movieGenresSearch = movieGenreRepo.findById(mgi);
        if (movieGenresSearch.isPresent()){
            return "Este regístro ya está en la lista";
        }
        saveMovieGenre(movie, genre, mgi);
        return null;
    }

    private void saveMovieGenre(Movie movie, Genre genre, MovieGenresId mgi) {
        MovieGenres movieGenres = new MovieGenres();
        movieGenres.setGenre(genre);
        movieGenres.setMovie(movie);
        movieGenres.setId(mgi);
        movieGenreRepo.save(movieGenres);
    }

    public String deleteMovieGenre(Integer movieId, Integer genreId) {
        MovieGenresId movieGenresId = new MovieGenresId();
        movieGenresId.setGenreId(genreId);
        movieGenresId.setMovieId(movieId);
        Optional<MovieGenres> movieGenre = movieGenreRepo.findById(movieGenresId);
        if (movieGenre.isPresent()) {
            movieGenreRepo.deleteById(movieGenresId);
            return null;
        } else {
            return "Ese género no está relacionada con la película";
        }
    }



}
