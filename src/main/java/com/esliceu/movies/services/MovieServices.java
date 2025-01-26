package com.esliceu.movies.services;

import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieCompany;
import com.esliceu.movies.models.MovieCompanyId;
import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.repos.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServices {
    @Autowired
    MovieRepo movieRepo;
    @Autowired
    PermissionsServices permissionsServices;


    public Page<Movie> findAllMovies(Pageable pageable) {
        return movieRepo.findAll(pageable);
    }

    public String getMovieJson() {
        List<Movie> movies = movieRepo.findAll();
        List<String> names = movies.stream()
                .map(p -> p.getTitle())
                .collect(Collectors.toList());
        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public Movie findMovieById(Integer id) {
        return movieRepo.findById(id).get();
    }


    public String saveMovie(String username, String title, Integer budget, String homepage, String overview, BigDecimal popularity,
                            LocalDate releaseDate, Long revenue, Integer runtime, String movieStatus, String tagline,
                            BigDecimal voteAverage) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Crear películas");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        String validationMessage = validateMovieParams(title, budget, homepage, overview, popularity,
                releaseDate, revenue, runtime, movieStatus, tagline, voteAverage);
        if (validationMessage != null) return validationMessage;
        Movie movie = createMovie(title, budget, homepage, overview, popularity, releaseDate, revenue, runtime,
                movieStatus, tagline, voteAverage);
        movieRepo.save(movie);
        return null;
    }

    public String validateMovieParams(String title, Integer budget, String homepage, String overview, BigDecimal popularity,
                                      LocalDate releaseDate, Long revenue, Integer runtime, String movieStatus, String tagline,
                                      BigDecimal voteAverage) {
        if (title == null || title.trim().isEmpty() ||
                overview == null || overview.trim().isEmpty() ||
                releaseDate == null ||
                runtime == null ) {
            return "No puedes dejar vacío el título, el resumen, el año de estreno ni el tiempo de duración ";
        }
        return null;
    }

    private Movie createMovie(String title, Integer budget, String homepage, String overview, BigDecimal popularity,
                              LocalDate releaseDate, Long revenue, Integer runtime, String movieStatus, String tagline,
                              BigDecimal voteAverage) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setBudget(budget);
        movie.setHomepage(homepage);
        movie.setOverview(overview);
        movie.setPopularity(popularity);
        movie.setReleaseDate(releaseDate);
        movie.setRevenue(revenue);
        movie.setRuntime(runtime);
        movie.setMovieStatus(movieStatus);
        movie.setTagline(tagline);
        movie.setVoteAverage(voteAverage);
        return movie;
    }

    public String deleteMovie(Integer id, String username) {
        String necessaryPermission = permissionsServices.checkPermisions(username, "Eliminar películas");
        if (necessaryPermission == null) return "No tienes el permiso necesario";
        try {
            movieRepo.deleteById(id);
            return null;
        } catch (Exception e) {
            return "Ha habido un error al eliminar la película";
        }

    }

    public String updateMovie(Integer id, String title, Integer budget, String homepage, String overview, BigDecimal popularity,
                              LocalDate releaseDate, Long revenue, Integer runtime, String movieStatus, String tagline,
                              BigDecimal voteAverage) {
        Optional<Movie> existingMovie = movieRepo.findById(id);
        if (existingMovie.isEmpty()) return "No existe esa película";
        Movie updatedMovie = existingMovie.get();
        updateTitle(updatedMovie, title);
        updateBudget(updatedMovie, budget);
        updateHomepage(updatedMovie, homepage);
        updateOverview(updatedMovie, overview);
        updatePopularity(updatedMovie, popularity);
        updateReleaseDate(updatedMovie, releaseDate);
        updateRevenue(updatedMovie, revenue);
        updateRuntime(updatedMovie, runtime);
        updateMovieStatus(updatedMovie, movieStatus);
        updateTagline(updatedMovie, tagline);
        updateVoteAverage(updatedMovie, voteAverage);
        movieRepo.save(updatedMovie);
        return null;
    }

    private void updateTitle(Movie movie, String title) {
        if (title != null && !title.trim().isEmpty()) {
            movie.setTitle(title);
        }
    }

    private void updateBudget(Movie movie, Integer budget) {
        if (budget != null) {
            movie.setBudget(budget);
        }
    }

    private void updateHomepage(Movie movie, String homepage) {
        if (homepage != null && !homepage.trim().isEmpty()) {
            movie.setHomepage(homepage);
        }
    }

    private void updateOverview(Movie movie, String overview) {
        if (overview != null && !overview.trim().isEmpty()) {
            movie.setOverview(overview);
        }
    }

    private void updatePopularity(Movie movie, BigDecimal popularity) {
        if (popularity != null) {
            movie.setPopularity(popularity);
        }
    }

    private void updateReleaseDate(Movie movie, LocalDate releaseDate) {
        if (releaseDate != null) {
            movie.setReleaseDate(releaseDate);
        }
    }

    private void updateRevenue(Movie movie, Long revenue) {
        if (revenue != null) {
            movie.setRevenue(revenue);
        }
    }

    private void updateRuntime(Movie movie, Integer runtime) {
        if (runtime != null) {
            movie.setRuntime(runtime);
        }
    }

    private void updateMovieStatus(Movie movie, String movieStatus) {
        if (movieStatus != null && !movieStatus.trim().isEmpty()) {
            movie.setMovieStatus(movieStatus);
        }
    }

    private void updateTagline(Movie movie, String tagline) {
        if (tagline != null && !tagline.trim().isEmpty()) {
            movie.setTagline(tagline);
        }
    }

    private void updateVoteAverage(Movie movie, BigDecimal voteAverage) {
        if (voteAverage != null) {
            movie.setVoteAverage(voteAverage);
        }
    }

    public Page<Movie> findMoviesByActionType(String condition, String actionType, Pageable pageable) {
        Page<Movie> moviePage;
        switch (actionType) {
            case "searchByTitle":
                return getMoviesByTitle(condition, pageable);
            case "searchByActor":
                return getMoviesByActor(condition, pageable);
            case "searchByCharacter":
                return getMoviesByCharacter(condition, pageable);
            case "searchByGenre":
                return getMoviesByGenre(condition, pageable);
            case "searchByDirector":
                return getMoviesByDirector(condition, pageable);
            default:
                return Page.empty();
        }
    }

    private Page<Movie> getMoviesByDirector(String condition, Pageable pageable) {
        Page<Movie> moviePage;
        moviePage = movieRepo.findDistinctByMovieCrewPersonPersonNameAndMovieCrewIdJob(condition, "Director", pageable);
        if (moviePage.getTotalElements() == 0) {
            moviePage = movieRepo.findDistinctByMovieCrewPersonPersonNameContainingAndMovieCrewIdJob(condition, "Director", pageable);
        }
        return moviePage;
    }

    private Page<Movie> getMoviesByGenre(String condition, Pageable pageable) {
        Page<Movie> moviePage;
        moviePage = movieRepo.findDistinctByMovieGenresGenreGenreName(condition, pageable);
        if (moviePage.getTotalElements() == 0) {
            moviePage = movieRepo.findDistinctByMovieGenresGenreGenreNameContaining(condition, pageable);
        }
        return moviePage;
    }

    private Page<Movie> getMoviesByCharacter(String condition, Pageable pageable) {
        Page<Movie> moviePage;
        moviePage = movieRepo.findDistinctByMovieCastIdCharacterName(condition, pageable);
        if (moviePage.getTotalElements() == 0) {
            moviePage = movieRepo.findDistinctByMovieCastIdCharacterNameContaining(condition, pageable);
        }
        return moviePage;
    }

    private Page<Movie> getMoviesByActor(String condition, Pageable pageable) {
        Page<Movie> moviePage;
        moviePage = movieRepo.findDistinctByMovieCastPersonPersonName(condition, pageable);
        if (moviePage.getTotalElements() == 0) {
            moviePage = movieRepo.findDistinctByMovieCastPersonPersonNameContaining(condition, pageable);
        }
        return moviePage;
    }

    private Page<Movie> getMoviesByTitle(String condition, Pageable pageable) {
        Page<Movie> moviePage;
        moviePage = movieRepo.findMovieByTitle(condition, pageable);
        if (moviePage.getTotalElements() == 0) {
            moviePage = movieRepo.findByTitleContaining(condition, pageable);
        }
        return moviePage;
    }
}
