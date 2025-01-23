package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.GenreRepo;
import com.esliceu.movies.repos.MovieGenresRepo;
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


    public List<Genre> getMovieGenres(Movie movie) {
        List<Genre> genres = movie.getMovieGenres().stream()
                .map(movieGenres -> movieGenres.getGenre())
                .toList();
        return genres;
    }

    public String addMovieGenre (String genreName, Movie movie) {
        List<Genre> genres = genreRepo.findGenreByGenreName(genreName);
        if (genres.size() == 1) {
            Genre genre = genres.get(0);
            boolean exists = false;
            for (MovieGenres mg : movie.getMovieGenres()) {
                if (mg.getGenre().getGenreId().equals(genre.getGenreId())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                return "Esta compañia ya está añadida";
            }
            return createAndSaveMovieGenre(movie, genre);
        }
        return "Hay más de una compañia con ese nombre";
    }

    private String createAndSaveMovieGenre(Movie movie, Genre genre) {
        System.out.println("entra en create and save");
        MovieGenres movieGenres = new MovieGenres();
        movieGenres.setGenre(genre);
        movieGenres.setMovie(movie);

        MovieGenresId mgi = new MovieGenresId();
        mgi.setGenreId(genre.getGenreId());
        mgi.setMovieId(movie.getMovieId());

        movieGenres.setId(mgi);
        movieGenreRepo.save(movieGenres);

        return null;
    }


}
