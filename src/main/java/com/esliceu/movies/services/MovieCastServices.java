package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.GenderRepo;
import com.esliceu.movies.repos.MovieCastRepo;
import com.esliceu.movies.repos.MovieRepo;
import com.esliceu.movies.repos.PersonRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieCastServices {
    @Autowired
    PersonRepo personRepo;
    @Autowired
    GenderRepo genderRepo;
    @Autowired
    MovieCastRepo movieCastRepo;
    @Autowired
    MovieRepo movieRepo;


    public List<MovieCast> getMovieCast(Movie movie) {
        List<MovieCast> movieCast = movie.getMovieCast();
        return movieCast;
    }

    public String addMovieCast(String personName, String gender, String character_name, Integer castOrder, Integer movieId) {
        if (personName.isEmpty() || gender.isEmpty() || character_name.isEmpty() || castOrder == null)return "Falta rellenar un campo";
        List<Person> persons = personRepo.findPersonByPersonName(personName);
        if (persons.size() > 1) {
            return "Hay más de un actor con ese nombre";
        }else if (persons.isEmpty()) {
            return "No exite una persona con ese nombre";
        }
        Person person = persons.get(0);
        List<Gender> genders = genderRepo.findGenderByGender(gender);
        if (genders.isEmpty()) return "No existe ese género";
        Gender g = genders.get(0);
        Movie movie = movieRepo.findById(movieId).get();
        return createKeyAndMovieCast(movie, person, g, character_name, castOrder);
    }

    private String createKeyAndMovieCast(Movie movie, Person person, Gender gender, String characterName, Integer castOrder) {
        MovieCastId movieCastId = new MovieCastId();
        movieCastId.setMovieId(movie.getMovieId());
        movieCastId.setPersonId(person.getPersonId());
        movieCastId.setGenderId(gender.getGenderId());
        movieCastId.setCharacterName(characterName);
        Optional<MovieCast> movieCastSearch = movieCastRepo.findById(movieCastId);
        if (movieCastSearch.isPresent()){
            return "Este registro ya está en la lista";
        }
        saveMovieCast(movie, person, characterName, castOrder, gender, movieCastId);
        return null;
    }

    private void saveMovieCast(Movie movie, Person person, String characterName, Integer castOrder, Gender gender, MovieCastId movieCastId) {
        MovieCast movieCast = new MovieCast();
        movieCast.setCastOrder(castOrder);
        movieCast.setCharacterName(characterName);
        movieCast.setMovie(movie);
        movieCast.setGender(gender);
        movieCast.setPerson(person);
        movieCast.setId(movieCastId);
        movieCastRepo.save(movieCast);
    }

    public Integer getTheMinCastOrder(Movie movie) {
        Integer min = movie.getMovieCast().stream()
                .map(MovieCast::getCastOrder)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0);
        min = min + 1;
        return min;
    }

    public String deleteMovieCast(Integer movieId, Integer genderId, Integer personId, String characterName) {
       MovieCastId movieCastId = new MovieCastId();
       movieCastId.setPersonId(personId);
       movieCastId.setGenderId(genderId);
       movieCastId.setMovieId(movieId);
       movieCastId.setCharacterName(characterName);
        Optional<MovieCast> movieCast = movieCastRepo.findById(movieCastId);
        if (movieCast.isPresent()) {
            movieCastRepo.deleteById(movieCastId);
            return null;
        } else {
            return "Ese personaje no está relacionada con la película";
        }
    }

    public String getActorsJson() {
        List <Person> personsMovieCast = personRepo.findByMovieCastIsNotEmpty();
        List<String> personNames = personsMovieCast.stream()
                .map(p -> p.getPersonName())
                .collect(Collectors.toList());
        Gson gson = new Gson();
        String result = gson.toJson(personNames);
        return result;
    }

    public String getCharactersJson() {
        List<String> charactersName = movieCastRepo.findAll()
                .stream().map(mc -> mc.getCharacterName()).toList();
        Gson gson = new Gson();
        String result = gson.toJson(charactersName);
        return result;
    }
}
