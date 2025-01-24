package com.esliceu.movies.services;
import com.esliceu.movies.models.Country;
import com.esliceu.movies.models.Genre;
import com.esliceu.movies.repos.CountryRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QueriesServices {

    @Autowired
    MovieServices movieServices;

    @Autowired
    GenreServices genreServices;

    @Autowired
    MovieCastServices movieCastServices;

    @Autowired
    MovieCrewServices movieCrewServices;


    public String getJson(String actionSelect) {
        switch (actionSelect){
            case "movieTitol":
                return movieServices.getMovieJson();
            case "actor":
               return movieCastServices.getActorsJson();
            case "character":
               return movieCastServices.getCharactersJson();
            case "genre":
               return genreServices.getGenreJson();
            case "director":
              return   movieCrewServices.getDirectorJson();

        }
        return null;
    }
}
