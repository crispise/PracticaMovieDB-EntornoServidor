package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieKeywordsServices {
    @Autowired
    KeywordRepo keywordRepo;
    @Autowired
    MovieKeywordRepo movieKeywordRepo;
    @Autowired
    MovieRepo movieRepo;


    public List<MovieKeywords> getMovieKeywords(Movie movie) {
        List<MovieKeywords> movieKeywords = movie.getMovieKeywords();
        return movieKeywords;
    }

    public String addMovieKeyword(String keywordName, Integer movieId) {
        if (keywordName.isEmpty()) return "Falta introducir la palabra";
        Movie movie = movieRepo.findById(movieId).get();
        List<Keyword> keywords = keywordRepo.findKeywordByKeywordName(keywordName);
        if (keywords.size() > 1) {
            return "Hay más de una palabra clave con ese nombre";
        }else if (keywords.isEmpty()){
            return "No hay palabras claves con ese nombre";
        }
        Keyword keyword = keywords.get(0);
        return createKeyAndMovieKeyword(movie, keyword);
    }

    private String createKeyAndMovieKeyword(Movie movie, Keyword keyword) {
        MovieKeywordId mKi = new MovieKeywordId();
        mKi.setKeywordId(keyword.getKeywordId());
        mKi.setMovieId(movie.getMovieId());
        Optional<MovieKeywords> movieKeywordSearch = movieKeywordRepo.findById(mKi);
        if (movieKeywordSearch.isPresent()){
            return "Este registro ya está en la lista";
        }
        saveMovieKeyword(movie, keyword, mKi);
        return null;
    }

    private void saveMovieKeyword(Movie movie, Keyword keyword, MovieKeywordId mKi) {
        MovieKeywords movieKeyword = new MovieKeywords();
        movieKeyword.setKeyword(keyword);
        movieKeyword.setMovie(movie);
        movieKeyword.setId(mKi);
        movieKeywordRepo.save(movieKeyword);
    }

    public String deleteMovieKeyword(Integer movieId, Integer keywordId) {
        MovieKeywordId movieKeywordId = new MovieKeywordId();
        movieKeywordId.setMovieId(movieId);
        movieKeywordId.setKeywordId(keywordId);

        Optional<MovieKeywords> movieKeyword = movieKeywordRepo.findById(movieKeywordId);
        if (movieKeyword.isPresent()) {
            movieKeywordRepo.deleteById(movieKeywordId);
            return null;
        } else {
            return "Esa compañia no está relacionada con la película";
        }
    }

}
