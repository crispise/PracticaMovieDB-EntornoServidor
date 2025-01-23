package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieLanguagesServices {
    @Autowired
    LanguageRepo languageRepo;
    @Autowired
    MovieLanguagesRepo movieLanguagesRepo;
    @Autowired
    LanguageRoleRepo languageRoleRepo;


    public List<MovieLanguages> getMovieLanguages(Movie movie) {
        List<MovieLanguages>  movieLanguages = movie.getMovieLanguages();
        return movieLanguages;
    }


    public String addMovieLanguage(String languageName, Movie movie, String languageRole) {
        List<Language> languages = languageRepo.findLanguageByLanguageName(languageName);
        List<LanguageRole> languageRoles = languageRoleRepo.findLanguageRoleByLanguageRole(languageRole);
        if (languages.size() == 1) {
            Language language = languages.get(0);
            boolean exists = checkIfMovieLanguageAlredyExists(movie, language, languageRoles);
            if (exists) {
                return "Este lenguaje y rol ya están añadidos";
            }
            return createAndSaveMovieKeyword(movie, language, languageRoles.get(0));
        }
        return "Hay más de un lenguaje con ese nombre";
    }

    private static boolean checkIfMovieLanguageAlredyExists(Movie movie, Language language, List<LanguageRole> languageRoles) {
        for (MovieLanguages ml : movie.getMovieLanguages()) {
            if (ml.getLanguage().getLanguageId().equals(language.getLanguageId())) {
                for (LanguageRole lr : languageRoles) {
                    if (ml.getLanguageRole().getRoleId().equals(lr.getRoleId())) {
                       return true;
                    }
                }
            }
        }
        return false;
    }

    private String createAndSaveMovieKeyword(Movie movie, Language language, LanguageRole languageRole) {
        MovieLanguages movieLanguages = new MovieLanguages();
        movieLanguages.setLanguage(language);
        movieLanguages.setMovie(movie);
        movieLanguages.setLanguageRole(languageRole);
        MovieLanguagesId movieLanguagesId = new MovieLanguagesId();
        movieLanguagesId.setLanguageId(language.getLanguageId());
        movieLanguagesId.setMovieId(movie.getMovieId());
        movieLanguagesId.setLanguageRoleId(languageRole.getRoleId());
        movieLanguages.setId(movieLanguagesId);
        movieLanguagesRepo.save(movieLanguages);

        return null;
    }

}
