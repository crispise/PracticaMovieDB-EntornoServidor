package com.esliceu.movies.services;

import com.esliceu.movies.models.*;
import com.esliceu.movies.repos.GenderRepo;
import com.esliceu.movies.repos.MovieCastRepo;
import com.esliceu.movies.repos.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieCastServices {
    @Autowired
    PersonRepo personRepo;
    @Autowired
    GenderRepo genderRepo;
    @Autowired
    MovieCastRepo movieCastRepo;


    public List<MovieCast> getMovieCast(Movie movie) {
        List<MovieCast> movieCast = movie.getMovieCast();
        return movieCast;
    }

    public String addMovieCast(String personName, String gender, String character_name, Integer castOrder, Movie movie) {
        List<Person> persons = personRepo.findPersonByPersonName(personName);
        if (persons.size() == 1) {
            Person person = persons.get(0);
            boolean exists = checkIfMovieCastAlredyExists(movie, person, character_name);
            if (exists) {
                return "Este actor ya esta añadido";
            }
            return createAndSaveMovieCast(movie, person, gender, character_name, castOrder);
        }
        return "Hay más de una compañia con ese nombre";
    }

    private static boolean checkIfMovieCastAlredyExists(Movie movie, Person person, String character_name) {
        for (MovieCast mc : movie.getMovieCast()) {
            if (mc.getPerson().getPersonId().equals(person.getPersonId())) {
                if (mc.getCharacterName().equals(character_name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String createAndSaveMovieCast(Movie movie, Person person, String genderString, String characterName, Integer castOrder) {
        List<Gender> genders = genderRepo.findGenderByGender(genderString);
        Gender gender = genders.get(0);

        MovieCast movieCast = new MovieCast();
        movieCast.setCastOrder(castOrder);
        movieCast.setCharacterName(characterName);
        movieCast.setMovie(movie);
        movieCast.setGender(gender);
        movieCast.setPerson(person);

        MovieCastId movieCastId = new MovieCastId();
        movieCastId.setMovieId(movie.getMovieId());
        movieCastId.setPersonId(person.getPersonId());
        movieCastId.setGenderId(gender.getGenderId());

        movieCast.setId(movieCastId);
        movieCastRepo.save(movieCast);
        return null;
    }

    public Integer getTheMinCastOrder(Movie movie) {
        Integer min = movie.getMovieCast().stream()
                .map(mc -> mc.getCastOrder())
                .max(Integer::compareTo).orElse(null);
        min = min + 1;
        return min;
    }

    public String deleteMovieCast(Integer movieId, Integer genderId, Integer personId) {
       MovieCastId movieCastId = new MovieCastId();
       movieCastId.setPersonId(personId);
       movieCastId.setGenderId(genderId);
       movieCastId.setMovieId(movieId);

        Optional<MovieCast> movieCast = movieCastRepo.findById(movieCastId);
        if (movieCast.isPresent()) {
            movieCastRepo.deleteById(movieCastId);
            return null;
        } else {
            return "Ese personaje no está relacionada con la película";
        }
    }
}
