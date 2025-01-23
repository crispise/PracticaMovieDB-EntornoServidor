package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieKeywordsServices {
    @Autowired
    KeywordRepo keywordRepo;
    @Autowired
    MovieKeywordRepo movieKeywordRepo;


    public List<MovieKeywords> getMovieKeywords(Movie movie) {
        List<MovieKeywords> movieKeywords = movie.getMovieKeywords();
        return movieKeywords;
    }

    public String addMovieKeyword(String keywordName, Movie movie) {
        List<Keyword> keywords = keywordRepo.findKeywordByKeywordName(keywordName);
        if (keywords.size() == 1) {
            Keyword keyword = keywords.get(0);
            boolean exists = false;
            for (MovieKeywords ky : movie.getMovieKeywords()) {
                if (ky.getKeyword().getKeywordId().equals(keyword.getKeywordId())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                return "Esta palabra clave ya está añadida";
            }
            return createAndSaveMovieKeyword(movie, keyword);
        }
        return "Hay más de una palabra clave con ese nombre";
    }

    private String createAndSaveMovieKeyword(Movie movie, Keyword keyword) {
        MovieKeywords movieKeyword = new MovieKeywords();
        movieKeyword.setKeyword(keyword);
        movieKeyword.setMovie(movie);
        MovieKeywordId mKi = new MovieKeywordId();
        mKi.setKeywordId(keyword.getKeywordId());
        mKi.setMovieId(movie.getMovieId());
        movieKeyword.setId(mKi);
        movieKeywordRepo.save(movieKeyword);
        return null;
    }

}
