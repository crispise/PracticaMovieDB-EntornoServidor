package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieLanguagesServices {
    @Autowired
    LanguageRepo languageRepo;
    @Autowired
    MovieLanguagesRepo movieLanguagesRepo;
    @Autowired
    LanguageRoleRepo languageRoleRepo;
    @Autowired
    MovieRepo movieRepo;


    public List<MovieLanguages> getMovieLanguages(Movie movie) {
        List<MovieLanguages>  movieLanguages = movie.getMovieLanguages();
        return movieLanguages;
    }


    public String addMovieLanguage(String languageName, Integer movieId, String languageRole) {
        if (languageName.isEmpty() || languageRole.isEmpty())return "Falta rellenar un campo";
        Movie movie = movieRepo.findById(movieId).get();
        List<Language> languages = languageRepo.findLanguageByLanguageName(languageName);
        if (languages.size() > 1) {
            return "Hay más de un lenguaje con ese nombre";
        } else if (languages.isEmpty()) {
            return "No hay ningún lenguaje con ese nombre";
        }
        List<LanguageRole> languageRoles = languageRoleRepo.findLanguageRoleByLanguageRole(languageRole);
        if (languageRoles.isEmpty()) return "No hay ningún rol con ese nombre";
        Language language = languages.get(0);
        return createKeyAndMovieKeyword(movie, language, languageRoles.get(0));

    }

    private String createKeyAndMovieKeyword(Movie movie, Language language, LanguageRole languageRole) {
        MovieLanguagesId movieLanguagesId = new MovieLanguagesId();
        movieLanguagesId.setLanguageId(language.getLanguageId());
        movieLanguagesId.setMovieId(movie.getMovieId());
        movieLanguagesId.setLanguageRoleId(languageRole.getRoleId());
        Optional<MovieLanguages> movieLanguagesSearch = movieLanguagesRepo.findById(movieLanguagesId);
        if (movieLanguagesSearch.isPresent()){
            return "Este registro ya está en la lista";
        }
        saveMovieLanguages(movie, language, languageRole, movieLanguagesId);
        return null;
    }

    private void saveMovieLanguages(Movie movie, Language language, LanguageRole languageRole, MovieLanguagesId movieLanguagesId) {
        MovieLanguages movieLanguages = new MovieLanguages();
        movieLanguages.setLanguage(language);
        movieLanguages.setMovie(movie);
        movieLanguages.setLanguageRole(languageRole);
        movieLanguages.setId(movieLanguagesId);
        movieLanguagesRepo.save(movieLanguages);
    }


    public String deleteMovieLanguage(Integer movieId, Integer languageId, Integer languageRoleId) {
        MovieLanguagesId movieLanguagesId = new MovieLanguagesId();
        movieLanguagesId.setLanguageRoleId(languageRoleId);
        movieLanguagesId.setMovieId(movieId);
        movieLanguagesId.setLanguageId(languageId);

        Optional<MovieLanguages> movieLanguages = movieLanguagesRepo.findById(movieLanguagesId);
        if (movieLanguages.isPresent()) {
            movieLanguagesRepo.deleteById(movieLanguagesId);
            return null;
        } else {
            return "Ese idioma no está relacionada con la película";
        }
    }

}
